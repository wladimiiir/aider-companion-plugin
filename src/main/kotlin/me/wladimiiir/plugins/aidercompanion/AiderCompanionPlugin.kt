package me.wladimiiir.plugins.aidercompanion

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.Disposable

@Service(Service.Level.PROJECT)
class AiderCompanionPlugin(private val project: Project) : Disposable {
    private val LOG = Logger.getInstance(AiderCompanionPlugin::class.java)
    private var restApiServer: RestApiServer? = null

    init {
        LOG.info("AiderCompanion plugin instance created")
    }

    fun startServer() {
        try {
            LOG.info("Attempting to start AiderCompanion server")
            if (restApiServer == null) {
                restApiServer = RestApiServer(project)
                restApiServer?.start()
                LOG.info("AiderCompanion server started successfully")
            } else {
                LOG.info("AiderCompanion server is already running")
            }
        } catch (e: Exception) {
            LOG.error("Failed to start AiderCompanion server", e)
        }
    }

    override fun dispose() {
        LOG.info("Disposing AiderCompanion")
        stopServer()
    }

    private fun stopServer() {
        try {
            LOG.info("Attempting to stop AiderCompanion server")
            restApiServer?.stop()
            restApiServer = null
            LOG.info("AiderCompanion server stopped successfully")
        } catch (e: Exception) {
            LOG.error("Failed to stop AiderCompanion server", e)
        }
    }
}

class AiderCompanionPluginStartupActivity : StartupActivity {
    private val LOG = Logger.getInstance(AiderCompanionPluginStartupActivity::class.java)

    override fun runActivity(project: Project) {
        try {
            LOG.info("AiderCompanionPluginStartupActivity: Initializing AiderCompanion")
            val plugin = project.getService(AiderCompanionPlugin::class.java)
            plugin.startServer()
            LOG.info("AiderCompanionPluginStartupActivity: AiderCompanion initialized successfully")
        } catch (e: Exception) {
            LOG.error("AiderCompanionPluginStartupActivity: Failed to initialize AiderCompanion", e)
        }
    }
}
