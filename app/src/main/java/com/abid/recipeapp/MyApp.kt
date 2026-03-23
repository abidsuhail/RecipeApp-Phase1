package com.abid.recipeapp

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val config: MutableMap<String?, String?> = HashMap()
        config["cloud_name"] = "dyatmlxim" // from Cloudinary dashboard
        MediaManager.init(this, config)
    }
}