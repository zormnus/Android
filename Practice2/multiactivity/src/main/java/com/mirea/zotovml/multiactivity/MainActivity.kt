package com.mirea.zotovml.multiactivity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var button1: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1 = findViewById(R.id.button)
    }

    fun onClickNewActivity(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        val text = "MIREA - Зотов Максим Леонидович"
        intent.putExtra(SecondActivity.TEXT, text)
        startActivity(intent)
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