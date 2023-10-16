package com.example.democamera.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.democamera.R
import com.example.democamera.databinding.ActivityRoomDataBinding

class RoomDataActivity : AppCompatActivity() {
    lateinit var binding:ActivityRoomDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRoomDataBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}