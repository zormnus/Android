package ru.mirea.zotovml.mireaproject

import android.R.attr.path
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


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
    private lateinit var urlText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webView)
        btn = view.findViewById(R.id.downloadBtn)
        urlText = view.findViewById(R.id.urlText)
        btn.setOnClickListener {
            Thread {
                try {
                    val content: String = getContent(urlText.text.toString()).toString()
                    webView.post {
                        webView.loadDataWithBaseURL(
                            urlText.text.toString(),
                            content,
                            "text/html",
                            "UTF-8",
                            urlText.text.toString()
                        )
                        Toast.makeText(
                            this.context, "Данные загружены", Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }.start()
        }
    }

    private fun getContent(path:String) : String? {
        var reader: BufferedReader? = null
        var stream: InputStream? = null
        var connection: HttpsURLConnection? = null
        try {
            val url = URL(path)
            connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            connection.readTimeout = 100000
            connection.connectTimeout = 100000
            connection.instanceFollowRedirects = true
            connection.useCaches = false
            connection.doInput = true
            val responseCode = connection.responseCode
            return if (responseCode == HttpsURLConnection.HTTP_OK) {
                connection.connect()
                stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                val buf = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    buf.append(line).append("\n")
                }
                buf.toString()
            } else {
                "$responseCode Error"
            }
        } finally {
            reader?.close()
            stream?.close()
            connection?.disconnect()
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