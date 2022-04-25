package ru.mirea.zotovml.audiorecord

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaRecorder
import android.os.Bundle
import ru.mirea.zotovml.audiorecord.R
import androidx.core.app.ActivityCompat
import kotlin.Throws
import android.os.Environment
import android.widget.Toast
import android.content.ContentValues
import android.provider.MediaStore
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Button
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    private var isWork = false
    private var startRecordButton: Button? = null
    private var stopRecordButton: Button? = null
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startRecordButton = findViewById<View>(R.id.button) as Button
        startRecordButton!!.setOnClickListener { view: View -> onClickStart(view) }
        stopRecordButton = findViewById<View>(R.id.button2) as Button
        stopRecordButton!!.setOnClickListener { view: View -> onRecordStop(view) }
        mediaRecorder = MediaRecorder()
        isWork = hasPermissions(this, *PERMISSIONS)
        if (!isWork) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS, REQUEST_CODE_PERMISSION
            )
        }
    }

    private fun onClickStart(view: View) {
        startRecordButton!!.isEnabled = false
        stopRecordButton!!.isEnabled = true
        stopRecordButton!!.requestFocus()
        try {
            startRecording()
        } catch (e: IOException) {
            Log.e(TAG, "IO Error " + e.message)
        }
    }

    @Throws(IOException::class)
    private fun startRecording() {
        if (isWork) {
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            if (audioFile == null) {
                audioFile = File(
                    getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                    "mirea.3gp"
                )
            }
            mediaRecorder!!.setOutputFile(audioFile!!.absolutePath)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            Log.e(TAG, "Start")
            Toast.makeText(this, "Запись началась.", Toast.LENGTH_LONG).show()
        }
    }

    private fun onRecordStop(view: View) {
        startRecordButton!!.isEnabled = true
        stopRecordButton!!.isEnabled = false
        startRecordButton!!.requestFocus()
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
                this, "Запись остановилась",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun processAudioFile() {
        Log.d(TAG, "processAudioFile")
        val values = ContentValues(4)
        val current = System.currentTimeMillis()
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile!!.name)
        values.put(MediaStore.Audio.Media.DATE_ADDED, (current / 1000).toInt())
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp")
        values.put(MediaStore.Audio.Media.DATA, audioFile!!.absolutePath)
        val contentResolver = contentResolver
        Log.d(TAG, "audioFile: " + audioFile!!.canRead())
        val baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val newUri = contentResolver.insert(baseUri, values)
        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val REQUEST_CODE_PERMISSION = 100
        fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
            if (context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission!!)
                        == PackageManager.PERMISSION_DENIED
                    ) {
                        return false
                    }
                }
                return true
            }
            return false
        }
    }
}