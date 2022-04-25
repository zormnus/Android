package ru.mirea.zotovml.testthreads

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var textView:TextView
    private var counter:Int = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        val mainThread = Thread.currentThread()
        textView.text = "Текущий поток: ${mainThread.name}"
        mainThread.name = "MireaThread"
        textView.append("\nНовое имя потока: ${mainThread.name}")
    }

    fun Btnclick(view: View) {
        val runnable:Runnable = Runnable{
            val endTime:Long = System.currentTimeMillis() + 20 * 1000
            var numberThread:Int = counter++
            Log.i("ThreadProject","Запущен поток $numberThread")
            while (System.currentTimeMillis() < endTime){
                synchronized(this){
                    Thread.sleep(endTime - System.currentTimeMillis())
                }
                Log.i("ThreadProject", "выполнен поток $numberThread")
            }
        }
        val thread:Thread = Thread(runnable)
        thread.start()
    }
}