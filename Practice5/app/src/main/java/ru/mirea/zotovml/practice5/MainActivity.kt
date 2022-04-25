package ru.mirea.zotovml.practice5

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.core.content.getSystemService
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var listCountSensor: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listCountSensor = findViewById(R.id.list_view)
        val sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val arrayList:ArrayList<HashMap<String, Any>> = ArrayList()
        for (i in 0 until sensors.size){
            val sensorTypeList = HashMap<String, Any>()
            sensorTypeList["Name"] = sensors[i].name
            sensorTypeList["Value"] = sensors[i].maximumRange
            arrayList.add(sensorTypeList)
        }
        val mHistory = SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2
            , arrayOf("Name", "Value"), intArrayOf(android.R.id.text1, android.R.id.text2))
        listCountSensor.adapter = mHistory
    }
}