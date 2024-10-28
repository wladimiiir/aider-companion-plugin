package me.wladimiiir.plugins.aidercompanion

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.Logger

@Service(Service.Level.APP)
class AiderCompanionAppService : Disposable {
    private val LOG = Logger.getInstance(AiderCompanionAppService::class.java)
    private var restApiServer: RestApiServer? = null

    init {
        LOG.info("AiderCompanion plugin instance created")
    }

    fun startServer() {
        try {
            LOG.info("Attempting to start AiderCompanion server")
            if (restApiServer == null) {
                restApiServer = RestApiServer()
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
