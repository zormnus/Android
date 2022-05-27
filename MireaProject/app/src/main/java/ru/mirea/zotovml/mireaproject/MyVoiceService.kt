package ru.mirea.zotovml.mireaproject

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import java.lang.Exception

class MyVoiceService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val uri: Uri = Uri.parse("/storage/emulated/0/Android/data/ru.mirea.zotovml.mireaproject/cache/\$123.wav")
        mediaPlayer!!.setDataSource(applicationContext, uri)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        return START_STICKY
    }

    fun isPlayingNow() : Boolean = mediaPlayer != null

    override fun onDestroy() {
        try {
            mediaPlayer!!.stop()
        }catch (e:Exception) {}
        mediaPlayer = null
    }



}