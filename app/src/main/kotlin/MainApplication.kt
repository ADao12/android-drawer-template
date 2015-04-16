package com.michalfaber.drawertemplate

import kotlin.platform.platformStatic
import kotlin.properties.Delegates

public class MainApplication : BaseApplication() {
    companion object {

        //platformStatic allow access it from java code
        platformStatic public var graph: ApplicationComponent by Delegates.notNull()
    }

    override fun onCreate() {
        super<BaseApplication>.onCreate()
        graph = createApplicationComponent()
        graph.inject(this)
    }
}
