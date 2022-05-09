package ru.mirea.zotovml.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        val viewModel = ViewModelProvider(this)
            .get(ProgressViewModel::class.java)
        viewModel.getProgressState()!!
            .observe(this, Observer { isVisibleProgressBar: Boolean? ->
                if (isVisibleProgressBar == true) {
                    progressBar.setVisibility(View.VISIBLE)
                } else {
                    progressBar.setVisibility(View.GONE)
                }
            })
        viewModel.showProgress()
    }
}