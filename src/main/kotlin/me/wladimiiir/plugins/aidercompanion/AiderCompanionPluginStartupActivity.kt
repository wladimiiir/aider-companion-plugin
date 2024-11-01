package me.wladimiiir.plugins.aidercompanion;

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class AiderCompanionPluginStartupActivity : StartupActivity {
    private val LOG = Logger.getInstance(AiderCompanionPluginStartupActivity::class.java)

    override fun runActivity(project: Project) {
        try {
            LOG.info("AiderCompanionPluginStartupActivity: Initializing AiderCompanion")

            val application = ApplicationManager.getApplication().getService(AiderCompanionAppService::class.java)
            application.startServer()

            LOG.info("AiderCompanionPluginStartupActivity: AiderCompanion initialized successfully")
        } catch (e: Exception) {
            LOG.error("AiderCompanionPluginStartupActivity: Failed to initialize AiderCompanion", e)
        }
    }
}
