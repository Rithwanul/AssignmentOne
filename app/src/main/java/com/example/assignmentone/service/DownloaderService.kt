package com.example.assignmentone.service

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.Service
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.IBinder
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class DownloaderService: Service() {


    private var downloadId: Long = 0
    private var query: DownloadManager.Query? = null
    private var cursor: Cursor? = null
    private var lastBytesDownloadedSoFar = 0
    private var totalBytes = 0


    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)

        val url = intent?.getStringExtra("Data")

        Timber.v(url)

        getData(url!!)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

     @SuppressLint("Range")
     fun getData(uri: String) {
        val arrayOfUrl = uri.split("/")
        val fileName = arrayOfUrl[arrayOfUrl.size - 1]

        val downloadManager =
            ContextCompat.getSystemService(
                this,
                DownloadManager::class.java
            ) as DownloadManager?
        val request = DownloadManager.Request(Uri.parse(uri))
        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or
                    DownloadManager.Request.NETWORK_MOBILE
        )


        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setTitle("Data Download")
        val extension = uri.substring(uri.lastIndexOf("."))
        request.setDescription("Android Data download using DownloadManager.")

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)


        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            fileName
        )


        request.setMimeType("*/*")
        val id = downloadManager!!.enqueue(request)


    }
}