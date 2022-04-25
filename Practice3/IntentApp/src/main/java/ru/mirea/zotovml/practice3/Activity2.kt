package ru.mirea.zotovml.practice3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Activity2 : AppCompatActivity() {
    companion object {
        const val KEY = "Time"
    }
    private lateinit var date:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        date = findViewById(R.id.textView)
        date.setText(intent.getStringExtra(KEY))
    }
}