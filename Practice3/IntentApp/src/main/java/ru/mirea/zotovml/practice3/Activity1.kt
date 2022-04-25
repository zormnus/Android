package ru.mirea.zotovml.practice3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class Activity1 : AppCompatActivity() {
    private lateinit var date_button: Button
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)
        date_button = findViewById(R.id.button)
    }

    @SuppressLint("SimpleDateFormat")
    fun sendTimeByButton(view: View) {
        val time = System.currentTimeMillis()
        val format = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(format)
        val dateString:String = sdf.format(Date(time))
        val activity2_intent = Intent(this, Activity2::class.java)
        activity2_intent.putExtra(Activity2.KEY, dateString)
        startActivity(activity2_intent)
    }
}