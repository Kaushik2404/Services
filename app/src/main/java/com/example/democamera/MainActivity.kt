package com.example.democamera

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.democamera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

//    lateinit var receiver: WifiMode
    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onclick()
//        receiver= WifiMode()

//        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
//            registerReceiver(receiver, it)
//        }

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        binding.stetWifi.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                wifiManager.isWifiEnabled = true
                binding.txtWifi.text = "WiFi is ON"
            } else {
                wifiManager.isWifiEnabled = false
                binding.txtWifi.text = "WiFi is OFF"
            }
        }


    }

    private fun onclick() {
        binding.start.setOnClickListener{
            startService(Intent(this@MainActivity,MusicServic::class.java))
        }

        binding.stop.setOnClickListener {
            stopService(Intent(this@MainActivity,MusicServic::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)

    }

    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN)) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    binding.stetWifi.isChecked = true
                    binding.txtWifi.text = "WiFi is ON"
                    Toast.makeText(this@MainActivity, "Wifi is On", Toast.LENGTH_SHORT).show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    binding.stetWifi.isChecked = false
                    binding.txtWifi.text = "WiFi is OFF"
                    Toast.makeText(this@MainActivity, "Wifi is Off", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}