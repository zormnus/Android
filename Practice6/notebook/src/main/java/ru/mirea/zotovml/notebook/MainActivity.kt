package ru.mirea.zotovml.notebook

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.Key

class MainActivity : AppCompatActivity() {
    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
    }
    private val SAVED_PATH = "saved_path"
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var fileNameET: EditText
    private lateinit var Text: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileNameET = findViewById(R.id.editTextTextPersonName)
        Text = findViewById(R.id.editTextTextPersonName2)
        sharedPreferences = getPreferences(MODE_PRIVATE)
        val path = LoadPath()
        when {
            path != "Empty" -> {
                Text.setText(path?.let { getTextFromFile(it) }, TextView.BufferType.EDITABLE)
            }
        }
    }

    fun getTextFromFile(path: String) : String? {
        var fin:FileInputStream? = null
        try{
            val fileName = path.split('/')[4]
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
                fin?.close()
            } catch (ex:IOException) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
        return null
    }

    fun writeTextInFile(path: String,str:String) {
        val outputStream:FileOutputStream
        try {
            val fileName = path.split('/')[4]
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            outputStream.write(str.toByteArray())
            outputStream.close()
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun LoadPath() : String?{
        val result = sharedPreferences.getString(SAVED_PATH, "Empty")
        return result
    }

    fun SavePath(path:String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(SAVED_PATH, path)
        editor.apply()
        Toast.makeText(this, "Path $path saved", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SdCardPath")
    fun onSaveBtnClick(view: View) {
        var PATH = "/data/data/ru.mirea.zotovml.notebook/"
        PATH += fileNameET.text.toString()
        SavePath(PATH)
        writeTextInFile(PATH, Text.text.toString())
    }
}