package ru.mirea.zotovml.mireaproject

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MusicFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = MusicFragment::class.java.simpleName

    private val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    private var isWork = false
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        if (!isWork) {
            ActivityCompat.requestPermissions(this.context as Activity
                , PERMISSIONS, REQUEST_CODE_PERMISSION)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val kanyePlayButton = view.findViewById<Button>(R.id.button2)
        val kanyeStopButton = view.findViewById<Button>(R.id.button3)
        val startRecordBtn = view.findViewById<Button>(R.id.button5)
        val stopRecordBtn = view.findViewById<Button>(R.id.button4)
        startRecordBtn.setOnClickListener {
            startRecordBtn.isEnabled = false
            stopRecordBtn.isEnabled = true
            Log.e(TAG, "Start")
            stopRecordBtn.requestFocus()
            val intent = Intent(this.context, RecordService::class.java)
            this.context?.stopService(intent)
            Toast.makeText(this.context, "HI!", Toast.LENGTH_SHORT).show()
        }

        stopRecordBtn.setOnClickListener {
            startRecordBtn.isEnabled = true
            stopRecordBtn.isEnabled = false
            stopRecordBtn.requestFocus()
            val intent = Intent(this.context, RecordService::class.java)
            this.context?.stopService(intent)
        }
        kanyePlayButton.setOnClickListener {
            val intent = Intent(this.context,YeService::class.java)
            this.context?.startService(intent)
        }
        kanyeStopButton.setOnClickListener {
            val intent = Intent(this.context,YeService::class.java)
            this.context?.stopService(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false)
    }


    companion object {
        const val REQUEST_CODE_PERMISSION = 100
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MusicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}