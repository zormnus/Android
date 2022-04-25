package ru.mirea.zotovml.accelerometer

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener{
    private lateinit var azimuthTextView:TextView
    private lateinit var pitchTextView:TextView
    private lateinit var  rollTextView:TextView
    private lateinit var  sensorManager:SensorManager
    private lateinit var accelerometerSensor:Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        azimuthTextView = findViewById(R.id.textViewAzimuth)
        pitchTextView = findViewById(R.id.textViewPitch)
        rollTextView = findViewById(R.id.textViewRoll)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor!!.type == Sensor.TYPE_ACCELEROMETER){
            val valueAzimuth = p0.values[0]
            val valuePitch = p0.values[1]
            val  valueRoll = p0.values[2]
            azimuthTextView.text = "Azimuth: $valueAzimuth"
            pitchTextView.text = "Pitch: $valuePitch"
            rollTextView.text = "Roll: $valueRoll"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}