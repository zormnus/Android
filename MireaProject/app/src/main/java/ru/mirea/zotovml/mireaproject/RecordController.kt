package ru.mirea.zotovml.mireaproject

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import java.lang.Exception

class RecordController(private val context: Context) {
    private var mediaRecorder:MediaRecorder? = null


    fun start() : String {
        Log.d("!!!", "Start Recording")
        mediaRecorder = MediaRecorder()

        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        val audioPath = getAudioPath()
        Log.d("!!!", audioPath)
        mediaRecorder!!.setOutputFile(audioPath)
        mediaRecorder!!.prepare()
        mediaRecorder!!.start()
        return audioPath
    }

    fun isAudioRecording() : Boolean = mediaRecorder != null


    private fun getAudioPath() : String =
        "${context.externalCacheDirs[0].absolutePath}/$123.wav"

    fun stop() {
        Log.d("!!!", "Stop Recording")

        try {
            mediaRecorder!!.stop()
            mediaRecorder!!.release()
        }catch (e:Exception) {}
        mediaRecorder = null
    }

}