package com.ninos.mvp

import android.content.Context
import androidx.multidex.MultiDexApplication

/**
 * @author Ninos
 */
class Application : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = this
    }

    companion object {
        @JvmStatic
        lateinit var instance: Application
            private set
        lateinit var context: Context
    }
}