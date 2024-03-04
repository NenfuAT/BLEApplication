package com.k21091.bleapplication

import android.content.Context
import android.util.Log
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.RangeNotifier
import org.altbeacon.beacon.Region

class BleCentral(private val context: Context) : RangeNotifier, MonitorNotifier {

    private var beaconManager: BeaconManager = BeaconManager.getInstanceForApplication(context)
    private lateinit var mRegion: Region

    fun onBeaconServiceConnect() {
        mRegion = Region("iBeacon", null, null, null)
        val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
        beaconManager = BeaconManager.getInstanceForApplication(context)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT)) // iBeaconのフォーマット指定
    }

    fun startScanning() {
        if (!::mRegion.isInitialized) {
            // mRegion が初期化されていない場合、onBeaconServiceConnect() を呼び出して初期化する
            onBeaconServiceConnect()
        }
        beaconManager.addMonitorNotifier(this)
        beaconManager.addRangeNotifier(this)
        beaconManager.startMonitoring(mRegion)
        beaconManager.startRangingBeacons(mRegion)
    }

    fun stopScanning() {
        beaconManager.stopMonitoring(mRegion)
        beaconManager.stopRangingBeacons(mRegion)
    }

    fun onDestroy() {
        beaconManager.stopMonitoring(mRegion)
        beaconManager.stopRangingBeacons(mRegion)
    }

    override fun didEnterRegion(region: Region?) {
        Log.d("iBeacon", "Enter Region ${region?.uniqueId}")
    }

    override fun didExitRegion(region: Region?) {
        Log.d("iBeacon", "Exit Region ${region?.uniqueId}")
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        Log.d("BleCentral", "Detected Beacons:")
        beacons?.forEach { beacon ->
            Log.d("BleCentral", "UUID: ${beacon.id1}, Major: ${beacon.id2}, Minor: ${beacon.id3}, RSSI: ${beacon.rssi}")
        }
    }

    override fun didDetermineStateForRegion(state: Int, region: Region?) {
        Log.d("BleCentral", "Determine State: $state")
    }
}
