package ru.mirea.zotovml.looper

import android.app.Activity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button

class MainActivity : Activity() {
    private lateinit var btn: Button
    var myLooper: MyLooper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.button)
        myLooper = MyLooper()
        myLooper!!.start()
        btn.setOnClickListener(View.OnClickListener {
            val msg = Message()
            val bundle = Bundle()
            bundle.putString("KEY", "mirea")
            bundle.putString("WORK", "Intel Programmer")
            bundle.putInt("MyAge", myLooper!!.timeSleep)
            msg.data = bundle
            if (myLooper != null) {
                myLooper!!.handler?.sendMessage(msg)
            }
        })
    }
}