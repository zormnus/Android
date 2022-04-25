package ru.mirea.zotovml.looper

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import java.util.concurrent.ThreadLocalRandom

class MyLooper : Thread() {
    private var number = 0
    var handler: Handler? = null
    val timeSleep = ThreadLocalRandom.current().nextInt(1,15)
    @SuppressLint("HandlerLeak")
    override fun run() {
        Log.d("MyLooper", "run")
        Looper.prepare()
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                Log.d("MyLooper", number.toString() + ":" + msg.data.getString("KEY"))
                val age = msg.data.getInt("MyAge")
                val mywork = msg.data.getString("WORK")
                //Thread.sleep(age)
                Log.d("MyLooper", "Age: $age")
                Log.d("MyLooper", "Work: $mywork")
                number++
            }
        }
        Looper.loop()
    }
}