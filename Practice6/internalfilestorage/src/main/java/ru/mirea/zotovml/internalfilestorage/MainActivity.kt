package ru.mirea.zotovml.internalfilestorage

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import ru.mirea.zotovml.internalfilestorage.R
import android.widget.Toast
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
    }
    private lateinit var tv: TextView
    private val fileName = "mirea.txt"
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textView)
//        val str:String = "Hello, Mirea!"
//        val outputStream:FileOutputStream
//        try {
//            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
//            outputStream.write(str.toByteArray())
//            outputStream.close()
//        } catch (e:Exception){
//            e.printStackTrace()
//        }
        Thread (object : Runnable {
            override fun run() {
                try {
                    Thread.sleep(5000)
                } catch (e:InterruptedException) {
                    e.printStackTrace()
                }
                tv.post(object : Runnable {
                    override fun run() {
                        tv.text = getTextFromFile()
                    }
                })
            }
        }).start()
    }


    private fun getTextFromFile() : String? {
        var fin:FileInputStream? = null
        try{
            fin = openFileInput(fileName)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            val text = String(bytes)
            Log.d(LOG_TAG, text)
            return text
        } catch (e:IOException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        } finally {
            try {
                fin!!.close()
            } catch (ex:IOException) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
        return null
    }
}