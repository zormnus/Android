package ru.mirea.zotovml.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val REQUEST_CODE_PERMISSION_CAMERA = 100
        const val CAMERA_REQUEST = 0
    }
    private val TAG:String = MainActivity::class.java.simpleName
    private lateinit var imageView: ImageView
    private var isWork = false
    private lateinit var imageUri:Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        val cameraPermissionStatus = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA)
        val storagePermissionStatus = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED
            && storagePermissionStatus == PackageManager.PERMISSION_GRANTED){
            isWork = true
        }
        else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                , REQUEST_CODE_PERMISSION_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            imageView.setImageURI(imageUri)
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    fun onImageViewClick(view: View) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null && isWork){
            val photoFile: File = createImageFile()
            val authorizes:String = applicationContext.packageName + ".fileprovider"
            imageUri = FileProvider.getUriForFile(this,authorizes, photoFile)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
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

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA){
            isWork = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }
}