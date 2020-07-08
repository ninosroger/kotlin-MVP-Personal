package com.ninos.mvp

import androidx.multidex.MultiDexApplication

/**
 * Created by ninos on 2019/1/8.
 */
class Application : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @JvmStatic
        lateinit var instance: Application
            private set
    }
}