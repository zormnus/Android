package ru.mirea.zotovml.mireaproject

import android.annotation.SuppressLint
import android.net.wifi.WifiConfiguration
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var webView: WebView
    private lateinit var btn:Button
    private lateinit var cityName:EditText
    private lateinit var resultText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn = view.findViewById(R.id.downloadBtn)
        cityName = view.findViewById(R.id.urlText)
        resultText = view.findViewById(R.id.textView12)
        btn.setOnClickListener {
            if(cityName.text.toString().trim().equals(""))
                Toast.makeText(this.requireContext(), "Вы не ввели название города"
                    , Toast.LENGTH_LONG).show();
            else {
                val apiKey = "52a8fc856433f02db616f27aefcba0ed"
                val city = cityName.text
                val url =
                    "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric&lang=en"
                RemoteFetch().execute(url)
            }
        }

    }

    private inner class RemoteFetch : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            resultText.text = "Загружаю..."
        }

        override fun doInBackground(vararg strings: String?): String? {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            try {
                val url = URL(strings[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()


                val stream: InputStream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))


                val buffer = StringBuilder()
                var line: String? = ""


                while (reader.readLine().also { line = it } != null) buffer.append(line)
                    .append("\n")


                return buffer.toString()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                // Закрываем соединения
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            return null
        }

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObject = JSONObject(result)
                resultText.text = "Температура: " + jsonObject.getJSONObject("main")
                    .getDouble("temp")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}