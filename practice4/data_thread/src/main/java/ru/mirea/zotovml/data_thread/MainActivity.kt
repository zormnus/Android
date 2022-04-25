package ru.mirea.zotovml.data_thread

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var textView:TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        val runn1:Runnable = Runnable {
            textView.text = "runn1"
        }
        val runn2:Runnable = Runnable {
            textView.text = "runn2"
        }
        val runn3:Runnable = Runnable {
            textView.text = "runn3"
        }
        val t:Thread = Thread(Runnable {
            TimeUnit.SECONDS.sleep(2)
            runOnUiThread(runn1)
            TimeUnit.SECONDS.sleep(1)
            textView.postDelayed(runn3, 2000)
            textView.post(runn2)
        })
        t.start()
    }
}