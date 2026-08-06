package com.abid.recipeapp

import android.app.Application
import com.cloudinary.android.MediaManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MemoryCacheSettings
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        disableFirebaseCache()
        val config: MutableMap<String?, String?> = HashMap()
        config["cloud_name"] = "dyatmlxim" // from Cloudinary dashboard
        MediaManager.init(this, config)
    }

    private fun disableFirebaseCache() {
        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
            .build()

        FirebaseFirestore.getInstance().firestoreSettings = settings
    }
}