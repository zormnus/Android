package com.mirea.zotovml.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickShowDialog(view: View) {
        val dialogFragment:MyDialogFragment = MyDialogFragment()
        dialogFragment.show(supportFragmentManager, "mirea")
    }

    fun ProgressDialogShow(view: View) {
        val dialog = MyProgressDialogFragment()
        dialog.show(supportFragmentManager, "Progress")
    }
    fun DatePickerShow(view: View) {
        val dialog = MyDateDialogFragment()
        dialog.show(supportFragmentManager, "Date")
    }
    fun timePickerShow(view: View) {
        val dialog = MyTimeDialogFragment()
        dialog.show(supportFragmentManager, "Time")
    }
}