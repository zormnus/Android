package ru.mirea.zotovml.practice3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var openDialog:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openDialog = findViewById(R.id.button)
        openDialog.setOnClickListener {

        }
    }
}