package com.k21091.bleapplication

import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.util.Log
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter


class BleAdvertise(context: Context, uuid: String){
    private val beacon = Beacon.Builder()
        .setId1(uuid) // UUID
        .setId2("1") // major
        .setId3("80") // minor
        .setManufacturer(0x004C)
        .build()
    private val beaconParser = BeaconParser()
        .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")
    private val beaconTransmitter = BeaconTransmitter(context, beaconParser)

    fun AdvertiseStart(callback: (Boolean) -> Unit) {
        // 送信開始
        beaconTransmitter.startAdvertising(beacon, object : AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                // 成功時の処理
                callback(true)
                Log.d("BleAdvertise", "Advertisement started successfully.")
            }

            override fun onStartFailure(errorCode: Int) {
                // 失敗時の処理
                callback(false)
            }
        })
    }

    fun AdvertiseStop() {
        beaconTransmitter.stopAdvertising()
    }

    fun AdvertiseCheck() {
        if(beaconTransmitter.isStarted()){
            Log.d("status","Advertising")
        }else{
            Log.d("status","NotAdvertising")
        }
    }
}

