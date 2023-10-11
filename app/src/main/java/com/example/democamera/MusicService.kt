package com.example.democamera

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings


class MusicServic : Service() {
    lateinit var player : MediaPlayer
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player= MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI)
        player.isLooping=true
        player.start()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        player.stop()
    }
}