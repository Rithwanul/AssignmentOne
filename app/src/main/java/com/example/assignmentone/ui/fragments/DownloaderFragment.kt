package com.example.assignmentone.ui.fragments

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.assignmentone.R
import com.example.assignmentone.adapter.CharacterAdapter
import com.example.assignmentone.database.dao.CharacterDao
import com.example.assignmentone.databinding.FragmentCharacterBinding
import com.example.assignmentone.di.factory.ViewModelFactory
import com.example.assignmentone.viewmodel.CharacterViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class DownloaderFragment @Inject constructor(
    private val factory: ViewModelFactory,
    private val gson: Gson,
    private val characterItemDao: CharacterDao,
    private val characterAdapter: CharacterAdapter
) : Fragment() {

    private lateinit var viewModel: CharacterViewModel
    private lateinit var binding: FragmentCharacterBinding
    private var downloadId: Long = 0
    private var query: DownloadManager.Query? = null
    private var cursor: Cursor? = null
    private var lastBytesDownloadedSoFar = 0
    private var totalBytes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_character, container, false
        )
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url: String = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

//        val url: String = "https://telugucinema.com/wp-content/uploads/2022/03/ashwini-stills-300322-003.jpg"

        viewModel = ViewModelProvider(this, factory)[CharacterViewModel::class.java]

        CoroutineScope(Dispatchers.IO).launch {
            getData(url)
        }


    }

    @SuppressLint("Range")
    suspend fun getData(uri: String) {
        val arrayOfUrl = uri.split("/")
        val fileName = arrayOfUrl[arrayOfUrl.size - 1]

        val downloadManager =
            getSystemService(requireContext(), DownloadManager::class.java) as DownloadManager?
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




        while (id > 0) {
            try {
                Thread.sleep(300)
                query = DownloadManager.Query().setFilterById(
                    (DownloadManager.STATUS_PAUSED
                            or DownloadManager.STATUS_PENDING
                            or DownloadManager.STATUS_RUNNING).toLong()
                )
                val cursor = downloadManager.query(query)
                if (cursor.moveToFirst()) {

                    //get total bytes of the file
                    if (totalBytes <= 0) {
                        totalBytes =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    }
                    val bytesDownloadedSoFar =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    if (bytesDownloadedSoFar == totalBytes && totalBytes > 0) {
                        Thread.interrupted()
                    } else {
                        //update progress bar
                        withContext(Dispatchers.Main) {
                            Timber.d((bytesDownloadedSoFar - lastBytesDownloadedSoFar).toString())
                            lastBytesDownloadedSoFar = bytesDownloadedSoFar
                        }
                    }
                }
                cursor.close()
            } catch (e: Exception) {
                return
            }
        }

    }

    fun getMimeType(uri: String): String {
        val contentResolver = requireContext().contentResolver
        val typeMap = MimeTypeMap.getSingleton()
        val mimeType =
            typeMap.getExtensionFromMimeType(contentResolver.getType(uri.toUri()))

        return mimeType ?: "*/*"
    }

    /*class DownloadProgressCounter: Thread() {

        private var downloadId: Long = 0
        private var query: DownloadManager.Query? = null
        private var cursor: Cursor? = null
        private var lastBytesDownloadedSoFar = 0
        private var totalBytes = 0

        constructor(
            downloadId: Long
        ) {
            this.downloadId = downloadId
            query = DownloadManager.Query()
            query!!.setFilterById(this.downloadId)
        }

        override fun run() {
            super.run()

            while (downloadId > 0) {
                try {
                    sleep(300)
                    cursor = manager.query(query)
                    if (cursor.moveToFirst()) {

                        //get total bytes of the file
                        if (totalBytes <= 0) {
                            totalBytes =
                                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        }
                        val bytesDownloadedSoFar =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        if (bytesDownloadedSoFar == totalBytes && totalBytes > 0) {
                            interrupt()
                        } else {
                            //update progress bar
                            UiThreadStatement.runOnUiThread(Runnable {
                                mProgressBar.setProgress(mProgressBar.getProgress() + (bytesDownloadedSoFar - lastBytesDownloadedSoFar))
                                lastBytesDownloadedSoFar = bytesDownloadedSoFar
                            })
                        }
                    }
                    cursor.close()
                } catch (e: Exception) {
                    return
                }
            }
        }
    }*/
}