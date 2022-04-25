package ru.mirea.zotovml.workmanager

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class UploadWorker(@NonNull context: Context, @NonNull params:WorkerParameters):
    Worker(context,params){
    companion object{
        const val TAG:String = "UploadWorker"
    }
    override fun doWork(): Result {
        Log.d(TAG, "doWork: start")
        TimeUnit.SECONDS.sleep(10)
        Log.d(TAG, "doWork: end")
        return Result.success()
    }
}