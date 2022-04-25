package ru.mirea.zotovml.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }
}