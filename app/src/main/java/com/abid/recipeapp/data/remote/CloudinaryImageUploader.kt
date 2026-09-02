package com.abid.recipeapp.data.remote

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class CloudinaryImageUploader @Inject constructor() {
    suspend fun uploadImage(imageUri: Uri): String {
        return suspendCancellableCoroutine { continuation ->
            val reqId = MediaManager.get()
                .upload(imageUri)
                .unsigned("Abid_android")
                .callback(object : UploadCallback {

                    override fun onStart(requestId: String) {}

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}

                    override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                        val imageUrl = resultData["secure_url"] as? String
                        if (imageUrl != null) {
                            continuation.resume(imageUrl) // ✅ returns the URL
                        } else {
                            continuation.resumeWithException(Exception("Invalid image url"))
                        }
                    }

                    override fun onError(requestId: String?, error: ErrorInfo) {
                        continuation.resumeWithException(Exception(error.description)) // ✅ throws error
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                })
                .dispatch()

            continuation.invokeOnCancellation {
                MediaManager.get().cancelRequest(reqId)
            }
        }
    }
}