package com.example.democamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.democamera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener{
            startService(Intent(this@MainActivity,MusicServic::class.java))
        }

        binding.stop.setOnClickListener {
            stopService(Intent(this@MainActivity,MusicServic::class.java))
        }
    }
}