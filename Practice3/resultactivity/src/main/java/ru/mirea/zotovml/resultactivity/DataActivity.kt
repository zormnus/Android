package ru.mirea.zotovml.resultactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class DataActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var university_name:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        button = findViewById(R.id.button2)
        university_name = findViewById(R.id.setUniName)
        button.setOnClickListener {
            val data:String = university_name.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.KEY, data)
            setResult(RESULT_OK, intent)
            this.finish()
        }
    }
}