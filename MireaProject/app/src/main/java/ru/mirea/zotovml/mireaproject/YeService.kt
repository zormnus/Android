package ru.mirea.zotovml.mireaproject

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class YeService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer.create(this,R.raw.bound2)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
    }
}