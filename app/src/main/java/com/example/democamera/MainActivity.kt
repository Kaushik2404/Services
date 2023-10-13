package com.example.democamera

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import com.example.democamera.databinding.ActivityMainBinding
import com.example.democamera.floating.SimpleFloatingWindow
import com.example.democamera.floating.canDrawOverlays
import com.example.democamera.floating.showToast


class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

//    lateinit var receiver: WifiMode
    private lateinit var wifiManager: WifiManager
    private lateinit var wifiPanelLauncher: ActivityResultLauncher<Intent>

    private lateinit var simpleFloatingWindow: SimpleFloatingWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onclick()
//        getPermission()

//        receiver= WifiMode()

//        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
//            registerReceiver(receiver, it)
//        }

        simpleFloatingWindow = SimpleFloatingWindow(applicationContext)





        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        wifiPanelLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result here if needed
            }
        }

//        binding.stetWifi.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                val panelIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
//                wifiPanelLauncher.launch(panelIntent)
//
//                binding.txtWifi.text = "WiFi is ON"
//            } else {
//                val panelIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
//                wifiPanelLauncher.launch(panelIntent)
//
//                binding.txtWifi.text = "WiFi is OFF"
//            }
//        }


    }

    private fun onclick() {
        binding.start.setOnClickListener{
            startService(Intent(this@MainActivity,MusicServic::class.java))
        }

        binding.stop.setOnClickListener {
            stopService(Intent(this@MainActivity,MusicServic::class.java))
        }

        binding.floating.setOnClickListener {
//
            if (canDrawOverlays) {
                simpleFloatingWindow.show()
            } else {
                startManageDrawOverlaysPermission()
            }

//            if(!Settings.canDrawOverlays(this@MainActivity)){
//                getPermission()
//            }else{
//                val intent =Intent(this@MainActivity,FloatingService::class.java)
//                startService(intent)
//
//
//            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_DRAW_OVERLAY_PERMISSION -> {
                if (canDrawOverlays) {
                    simpleFloatingWindow.show()
                } else {
                    showToast("Permission is not granted!")
                }
            }
        }
    }

    private fun startManageDrawOverlaysPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${applicationContext.packageName}")
            ).let {
                startActivityForResult(it, REQUEST_CODE_DRAW_OVERLAY_PERMISSION)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_DRAW_OVERLAY_PERMISSION = 5
    }


//    private fun getPermission() {
//
//
//    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)){
//    val intent=Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package$packageName"))
//        startActivityForResult(intent,1)
//
//    }


//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(resultCode==1){
//            if(!Settings.canDrawOverlays(this@MainActivity)){
//                Toast.makeText(this@MainActivity,"Permission Denied" ,Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }

    override fun onStart() {
        super.onStart()
//        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
//        registerReceiver(wifiStateReceiver, intentFilter)
    }
    override fun onStop() {
        super.onStop()

//        if (canDrawOverlays) {
//            simpleFloatingWindow.show()
//        } else {
//            startManageDrawOverlaysPermission()
//        }
//        unregisterReceiver(wifiStateReceiver)

    }

    override fun onDestroy() {
        super.onDestroy()
//        SimpleFloatingWindow(applicationContext).show()
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