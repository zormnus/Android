package ru.mirea.zotovml.resultactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY = "UNIVERSITY"
    }
    private lateinit var university_name:TextView
    private lateinit var button: Button
    private val REQUEST_CODE = 134
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        university_name = findViewById(R.id.textView2)
        button.setOnClickListener {
            val intent = Intent(this, DataActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            val university: String? = data.getStringExtra(KEY)
            university_name.text = university
        }
    }
}