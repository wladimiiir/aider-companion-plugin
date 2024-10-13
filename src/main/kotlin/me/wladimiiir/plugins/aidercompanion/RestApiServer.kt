package me.wladimiiir.plugins.aidercompanion

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.nio.file.Path

class RestApiServer(private val project: Project) {
    private val LOG = Logger.getInstance(RestApiServer::class.java)
    private var server: NettyApplicationEngine? = null

    fun start() {
        try {
            LOG.info("Attempting to start REST API server on port 24337")
            server = embeddedServer(Netty, port = 24337) {
                install(ContentNegotiation) {
                    jackson()
                }
                routing {
                    get("/open-files") {
                        val openFiles = FileEditorManager.getInstance(project).selectedEditors.map {
                            val file = it.file ?: return@map "Unnamed"
                            val projectBasePath = project.basePath ?: return@map file.path
                            val relativePath = Path.of(projectBasePath).relativize(Path.of(file.path)).toString().replace("\\", "/")
                            relativePath
                        }
                        call.respond(openFiles)
                    }
                }
            }
            server?.start(wait = false)
            LOG.info("REST API server started successfully")
        } catch (e: Exception) {
            LOG.error("Failed to start REST API server", e)
        }
    }

    fun stop() {
        try {
            LOG.info("Attempting to stop REST API server")
            server?.stop(1000, 2000)
            LOG.info("REST API server stopped successfully")
        } catch (e: Exception) {
            LOG.error("Failed to stop REST API server", e)
        }
    }
}
