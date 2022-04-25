package com.mirea.zotovml.intentfilter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun browserCall(view: View) {
        val address = Uri.parse("https://www.mirea.ru/")
        val linkIntent = Intent(Intent.ACTION_VIEW, address)
        startActivity(linkIntent)
    }

    fun sendText(view: View) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Зотов Максим Леонидович")
        startActivity(Intent.createChooser(shareIntent, "Зотов М.Л."))
    }

}