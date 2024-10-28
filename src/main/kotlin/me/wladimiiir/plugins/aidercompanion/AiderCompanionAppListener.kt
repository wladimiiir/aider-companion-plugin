package me.wladimiiir.plugins.aidercompanion

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.application.ApplicationManager

class AiderCompanionAppListener : AppLifecycleListener {
    override fun appStarted() {
        ApplicationManager.getApplication().getService(AiderCompanionAppService::class.java).startServer()
    }
}
