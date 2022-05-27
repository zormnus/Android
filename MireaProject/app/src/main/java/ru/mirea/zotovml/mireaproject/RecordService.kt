package ru.mirea.zotovml.mireaproject

import android.app.Service
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import java.io.File

class RecordService : Service() {
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private val TAG = MusicFragment::class.java.simpleName

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    override fun onCreate() {
        mediaRecorder = MediaRecorder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRecording()
        return START_STICKY
    }

    private fun startRecording() {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
                Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()) {
            Log.d(TAG, "sd-card success")
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            if (audioFile == null) {
                audioFile = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "song.3gp")
            }
            mediaRecorder!!.setOutputFile(audioFile!!.absolutePath)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            Log.e(TAG, "Start")
            Toast.makeText(MusicFragment as Context, "Запись началась.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        Toast.makeText(MusicFragment::getContext.call(), "OnStop Record", Toast.LENGTH_SHORT).show()
        stopRecording()
        processAudioFile()
    }

    private fun stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording")
            Log.d(TAG, "File path: " + audioFile!!.absolutePath)
            mediaRecorder!!.stop()
            mediaRecorder!!.reset()
            mediaRecorder!!.release()
            mediaRecorder = null
            Toast.makeText(
                MusicFragment::getContext.call(), "Запись остановилась",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun processAudioFile() {
        Log.d(TAG, "process")
        val values = ContentValues(4)
        val current = System.currentTimeMillis()

        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile?.name)
        values.put(MediaStore.Audio.Media.DATE_ADDED, current / 1000)
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp")
        values.put(MediaStore.Audio.Media.DATA, audioFile?.absolutePath)

        val contentResolver = contentResolver
        Log.d(TAG, "audioFile: ${audioFile?.canRead()}")

        val baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val newUri = contentResolver.insert(baseUri, values)

        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri))
    }

}