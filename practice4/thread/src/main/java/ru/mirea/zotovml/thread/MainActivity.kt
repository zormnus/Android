package ru.mirea.zotovml.thread

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var showRes: TextView
    private lateinit var calculateBtn:Button
    private lateinit var input:EditText
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showRes = findViewById(R.id.textView2)
        calculateBtn = findViewById(R.id.button)
        input = findViewById(R.id.editTextText)
        Task1()
    }
    private fun Task1(){
        calculateBtn.setOnClickListener {
            val secondThread = Thread(Runnable {
                runOnUiThread(Runnable {
                    val days_and_classesCount = input.text.split(' ')
                    val days:Int = days_and_classesCount[0].toInt()
                    val classes_count:Int = days_and_classesCount[1].toInt()
                    val result:Int = classes_count / days
                    showRes.text = result.toString()
                })
            }).start()
        }
    }
}