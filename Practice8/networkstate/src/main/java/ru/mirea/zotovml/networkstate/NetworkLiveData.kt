package ru.mirea.zotovml.networkstate

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import java.lang.Boolean


class NetworkLiveData(context: Context) : LiveData<String>() {
    private var context: Context? = null
    private var broadcastReceiver: BroadcastReceiver? = null
    @SuppressLint("StaticFieldLeak")
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance:NetworkLiveData? = null
        fun getInstance(context: Context): NetworkLiveData {
            if (instance == null) {
                instance = NetworkLiveData(context.applicationContext)
            }
            return instance as NetworkLiveData
        }
    }

    init {
        if (instance != null)
            throw RuntimeException("Use getInstance() method to get the single " +
                    "instance of this class")
        this.context = context
    }

    private fun prepareReceiver(context:Context) {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                value = if (activeNetwork != null) {
                    val isConnected = activeNetwork.isConnectedOrConnecting
                    Boolean.toString(isConnected)
                } else "false"
            }
        }
        context.registerReceiver(broadcastReceiver, filter)
    }

    override fun onActive() {
        prepareReceiver(context!!)
    }

    override fun onInactive() {
        context!!.unregisterReceiver(broadcastReceiver)
        broadcastReceiver = null
    }
}