package ru.mirea.zotovml.mireaproject

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Functional : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    private var isWork = false
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var imageUri: Uri


    private lateinit var imageView: ImageView
    private lateinit var createPhotoBtn:Button

    private lateinit var startRecordingBtn: Button
    private lateinit var stopRecordingBtn: Button
    private lateinit var listenBtn: Button
    private lateinit var recordController:RecordController
    private lateinit var voiceFilePath:String
    private lateinit var stopListenBtn:Button

    private lateinit var XGravityText: TextView
    private lateinit var YGravityText: TextView
    private lateinit var ZGravityText: TextView
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        recordController = RecordController(this.requireContext())
        val cameraPermissionStatus = ContextCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.CAMERA)
        val storagePermissionStatus = ContextCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val recordPermissionStatus = ContextCompat.checkSelfPermission(this.requireContext(),
            Manifest.permission.RECORD_AUDIO)
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED
            && storagePermissionStatus == PackageManager.PERMISSION_GRANTED
            && recordPermissionStatus == PackageManager.PERMISSION_GRANTED){
            isWork = true
        }
        else{
            ActivityCompat.requestPermissions(this.requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.RECORD_AUDIO),
            REQUEST_CODE_PERMISSION_CAMERA)
        }
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_functional, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Functional().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        const val REQUEST_CODE_PERMISSION_CAMERA = 100
        const val CAMERA_REQUEST = 0
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,sensor,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.pictureView)
        XGravityText = view.findViewById(R.id.textView8)
        YGravityText = view.findViewById(R.id.textView9)
        ZGravityText = view.findViewById(R.id.textView10)
        createPhotoBtn = view.findViewById(R.id.cameraBtn)

        startRecordingBtn = view.findViewById(R.id.button8)
        stopRecordingBtn = view.findViewById(R.id.button9)
        listenBtn = view.findViewById(R.id.button10)
        stopListenBtn = view.findViewById(R.id.button11)

        startRecordingBtn.setOnClickListener {
            startRecordingBtn.isEnabled = false
            stopRecordingBtn.isEnabled = true
            voiceFilePath = recordController.start()
        }

        stopRecordingBtn.setOnClickListener {
            startRecordingBtn.isEnabled = true
            stopRecordingBtn.isEnabled = false
            recordController.stop()
        }

        listenBtn.setOnClickListener {
            val intent = Intent(this.requireContext(), MyVoiceService::class.java)
            this.context?.startService(intent)
        }

        stopListenBtn.setOnClickListener {
            val intent = Intent(this.requireContext(), MyVoiceService::class.java)
            this.context?.stopService(intent)
        }

        createPhotoBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (this.activity?.packageManager?.let { it1 -> cameraIntent.resolveActivity(it1) } != null
                && isWork){
                val photoFile: File = createImageFile()
                val authorizes:String = this.context?.packageName + ".fileprovider"
                imageUri = FileProvider.getUriForFile(this.requireContext(),authorizes, photoFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == AppCompatActivity.RESULT_OK){
            imageView.setImageURI(imageUri)
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA){
            isWork = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun createImageFile(): File {
        val timeStamp:String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_${timeStamp}_"
        val storageDirectory:File = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor!!.type == Sensor.TYPE_GRAVITY) {
            XGravityText.text = "X gravity = ${p0.values[0]}"
            YGravityText.text = "Y gravity = ${p0.values[1]}"
            ZGravityText.text = "Z gravity = ${p0.values[2]}"
        }
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}