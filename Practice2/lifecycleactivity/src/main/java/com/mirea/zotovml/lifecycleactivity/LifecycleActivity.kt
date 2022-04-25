package com.mirea.zotovml.lifecycleactivity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView

class LifecycleActivity : AppCompatActivity() {
    private lateinit var status: TextView
    private lateinit var int:Intent
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        status = findViewById(R.id.textView)
        status.setText("app was started")
        Log.i(TAG, "onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState()")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.i(TAG, "onPostCreate()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.i(TAG, "onPostResume()")
    }

    @SuppressLint("SetTextI18n")
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
        status.setText("app was paused")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    @SuppressLint("SetTextI18n")
    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart()")
        status.setText("app was resumed")
    }
}