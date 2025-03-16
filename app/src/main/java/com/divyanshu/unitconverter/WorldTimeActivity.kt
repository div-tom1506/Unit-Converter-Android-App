package com.divyanshu.unitconverter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class WorldTimeActivity : AppCompatActivity() {

    private lateinit var spinnerTimeZones: Spinner
    private lateinit var tvDigitalClock: TextView
    private lateinit var analogClockView: AnalogClockView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_time)

        spinnerTimeZones = findViewById(R.id.spinnerTimeZones)
        tvDigitalClock = findViewById(R.id.tvDigitalClock)
        analogClockView = findViewById(R.id.analogClockView)

        val timeZones = TimeZone.getAvailableIDs().sorted()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timeZones)
        spinnerTimeZones.adapter = adapter

        spinnerTimeZones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedZone = timeZones[position]
                updateTime(selectedZone)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        handler.post(object : Runnable {
            override fun run() {
                val selectedZone = spinnerTimeZones.selectedItem.toString()
                updateTime(selectedZone)
                analogClockView.invalidate()
                handler.postDelayed(this, 1000)
            }
        })

    }

    private fun updateTime(timeZoneId: String) {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone(timeZoneId)
        tvDigitalClock.text = sdf.format(Date())

        analogClockView.setTimeZone(timeZoneId)
    }

}
