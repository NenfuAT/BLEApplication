package com.k21091.bleapplication

import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var BleAdvertise: BleAdvertise
    private lateinit var BleCentral: BleCentral
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var checkButton: Button
    private lateinit var statusText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        // todo 権限
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH_ADMIN ,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.NEARBY_WIFI_DEVICES,
        )

        //許可したいpermissionを許可できるように
        if (!EasyPermissions.hasPermissions(this, *permissions)) {
            EasyPermissions.requestPermissions(this, "パーミッションに関する説明", 1, *permissions)
        }
        BleAdvertise=BleAdvertise(this,"74A23A96-A479-4330-AEFF-2421B6CF443C")
        BleCentral=BleCentral(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton = findViewById(R.id.StartButton)
        stopButton = findViewById(R.id.StopButton)
        checkButton = findViewById(R.id.CheckButton)
        statusText = findViewById(R.id.status)
        startButton.setOnClickListener {
            onStartButtonClick()
        }
        stopButton.setOnClickListener{
            onStopButtonClick()
        }
    }

    private fun onStartButtonClick() {
        BleCentral.startScanning()
        statusText.text = "Scanning started"
    }

    private fun onStopButtonClick() {
        BleCentral.stopScanning()
        statusText.text = "Scanning stopped"
    }

    override fun onDestroy() {
        super.onDestroy()
        BleCentral.onDestroy()
    }
}