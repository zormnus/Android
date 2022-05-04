package ru.mirea.zotovml.practice7

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.io.IOException
import java.net.Socket

class MainActivity : AppCompatActivity() {
    private val TAG:String = MainActivity::class.simpleName.toString()
    private lateinit var mTextView:TextView
    private val host = "time-a.nist.gov"
    private val port:Int = 13
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textView)
    }

    fun onClick(view: View) {
        val timeTask = GetTimeTask()
        timeTask.execute()
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetTimeTask : AsyncTask<Void, Void, String>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg p0: Void?): String {
            var timeResult = ""
            try{
                val socket = Socket(host, port)
                val reader = SocketUtils.getReader(socket)
                reader.readLine()
                timeResult = reader.readLine()
                Log.d(TAG, timeResult)
                socket.close()
            }catch (e:IOException) {
                e.printStackTrace()
            }

            return timeResult
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            mTextView.text = result
        }

    }
}