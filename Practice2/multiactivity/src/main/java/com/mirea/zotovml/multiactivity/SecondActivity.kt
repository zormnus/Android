package com.mirea.zotovml.multiactivity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mirea.zotovml.multiactivity.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    companion object {
        const val TEXT = "key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        textView = findViewById(R.id.textView)
        showText()
    }

    fun showText(){
        val text = intent.getStringExtra(TEXT)
        textView.text = text
    }

    override fun onStart() {
        super.onStart()
        Log.i(ContentValues.TAG, "onStart()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(ContentValues.TAG, "onRestoreInstanceState()")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.i(ContentValues.TAG, "onPostCreate()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(ContentValues.TAG, "onResume()")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.i(ContentValues.TAG, "onPostResume()")
    }

    @SuppressLint("SetTextI18n")
    override fun onPause() {
        super.onPause()
        Log.i(ContentValues.TAG, "onPause()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(ContentValues.TAG, "onSaveInstanceState()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(ContentValues.TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ContentValues.TAG, "onDestroy()")
    }

    @SuppressLint("SetTextI18n")
    override fun onRestart() {
        super.onRestart()
        Log.i(ContentValues.TAG, "onRestart()")
    }
}