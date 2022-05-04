package ru.mirea.zotovml.httpurlconnection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var ipText:TextView
    private lateinit var countryText:TextView
    private lateinit var regionText:TextView
    private lateinit var cityText:TextView
    private val url = "http://whatismyip.akamai.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ipText = findViewById(R.id.ipTextView)
        countryText = findViewById(R.id.countryTextView)
        cityText = findViewById(R.id.cityTextView)
        regionText = findViewById(R.id.regionTextView)
    }

    fun onGetIPClick(view: View) {
        val connectivityManager: ConnectivityManager? = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        var networkInfo:NetworkInfo? = null
        if (connectivityManager != null) {
            networkInfo = connectivityManager.activeNetworkInfo
        }

        if (networkInfo != null && networkInfo.isConnected) {
            DownloadPageTask().execute(url)
        }else {
            Toast.makeText(this, "Нет интернета.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class DownloadPageTask : AsyncTask<String, Void, UserData>() {
        override fun onPreExecute() {
            super.onPreExecute()
            ipText.text = "Загружаем..."
            cityText.text = "Загружаем..."
            regionText.text = "Загружаем..."
            countryText.text = "Загружаем..."
        }
        override fun doInBackground(vararg p0: String?): UserData? {
            return try {
                val ip:String = downloadIpInfo(p0[0].toString())
                getInformationByIp(ip)
            }catch (e:IOException) {
                e.printStackTrace()
                null
            }
        }

        private fun getInformationByIp(ip:String) :UserData?{
            try {
                val content: String = getAPIContent(
                    "http://ip-api.com/json/$ip",
                    "GET"
                )
                val responseJson = JSONObject(content)
                val country = responseJson["country"].toString()
                val region = responseJson["regionName"].toString()
                val city = responseJson["city"].toString()
                return UserData(ip, city, country, region)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: UserData?) {
            ipText.text = result?.getIp()
            countryText.text = result?.getCountry()
            cityText.text = result?.getCity()
            regionText.text = result?.getRegion()
            super.onPostExecute(result)
        }


        private fun getAPIContent(address:String, method:String) : String {
            var data = ""
            val inputStream:InputStream? = null
            try {
                val url = URL(address)
                val connection:HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.readTimeout = 100000
                connection.connectTimeout = 100000
                connection.requestMethod = method
                connection.instanceFollowRedirects = true
                connection.useCaches = false
                connection.doInput = true
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val bos = ByteArrayOutputStream()
                    var read = 0
                    while ((inputStream.read().also { read = it }) != -1) {
                        bos.write(read)
                    }

                    val result = bos.toByteArray()
                    bos.close()
                    data = String(result)
                }
                else {
                    data = "${connection.responseMessage} + . Error Code: $responseCode"
                }
                connection.disconnect()
            }catch (e:MalformedURLException) {
                e.printStackTrace()
            }catch (e:IOException) {
                e.printStackTrace()
            }finally {
                inputStream?.close()
            }
            return data
        }

        private fun downloadIpInfo(ip:String) : String {
            return getAPIContent(ip, "GET")
        }
    }
}