package com.example.democamera

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import java.util.Calendar

class FloatingService : Service(){
    var LAYOUT_FLAG:Int?=0
    lateinit var manager:WindowManager
    lateinit var imgClose:ImageView
    lateinit var floatview:View

    var hight:Int?=0
    var width:Int?=0

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            LAYOUT_FLAG= WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        }else{
            LAYOUT_FLAG= WindowManager.LayoutParams.TYPE_PHONE

        }

         floatview=LayoutInflater.from(this).inflate(R.layout.float_layout,null)

        var layputParams  =  WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG!!,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT)

        layputParams.gravity = Gravity.TOP or Gravity.RIGHT
        layputParams.x=0
        layputParams.y=100

        var imageParms = WindowManager.LayoutParams(140,140, LAYOUT_FLAG!!,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)


    imageParms.gravity=Gravity.BOTTOM or Gravity.CENTER
    imageParms.y=100

    manager= getSystemService(WINDOW_SERVICE) as WindowManager
    imgClose= ImageView(this)
    imgClose.setImageResource(R.drawable.baseline_clear_24)
    imgClose.visibility=View.INVISIBLE
    manager.addView(imgClose,imageParms)
    manager.addView(floatview,layputParams)
    floatview.visibility=View.VISIBLE

    hight=manager.defaultDisplay.height
    width=manager.defaultDisplay.width

    val imgwidget:ImageView=floatview.findViewById<ImageView>(R.id.zoom)
    val flayout:LinearLayout=floatview.findViewById(R.id.flayout)

    flayout.setOnTouchListener { view, motionEvent ->

        var initialx:Int?=0
        var initialy:Int?=0
        var initialTOuchx:Float?=0.0f
        var initialTouchy:Float?=0.0f
        var maxclickDuration:Int=200
        var startClick:Long?=0

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {

                startClick=Calendar.getInstance().timeInMillis
                imgClose.visibility=View.VISIBLE
                initialx=layputParams.x
                initialy=layputParams.y

                initialTOuchx=motionEvent.rawX
                initialTouchy=motionEvent.rawY
                return@setOnTouchListener true
            }



            MotionEvent.ACTION_UP ->{
                var clickDuration:Long = Calendar.getInstance().timeInMillis - startClick!!
                imgClose.visibility=View.VISIBLE
                layputParams.x = initialx!! + (initialTOuchx!! - motionEvent.rawX).toInt()
                layputParams.y=initialy!! + (motionEvent.rawY - initialTouchy!! ).toInt()

                if(clickDuration<maxclickDuration){
                    Toast.makeText(this@FloatingService,"hello",Toast.LENGTH_SHORT).show()
                }
                else{
                    if(layputParams.y>(hight!!*0.6)){
                        stopSelf()
                    }
                }
                return@setOnTouchListener  true
            }

            MotionEvent.ACTION_MOVE ->{
                layputParams.x= initialx!! +   (initialTOuchx!!- motionEvent.rawX).toInt()
                layputParams.y=initialy!! + (motionEvent.rawY - initialTouchy!!).toInt()

                manager.updateViewLayout(floatview,layputParams)

                if(layputParams.y  >(hight!! * 0.6 )){
                        imgClose.setImageResource(R.drawable.baseline_clear_24)
                }else{
                    imgClose.setImageResource(R.drawable.baseline_open_in_full_24)
                }
            return@setOnTouchListener true
            }


        }
        return@setOnTouchListener false
    }
    return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        if(floatview!=null){
            manager.removeView(floatview)
        }
        if(imgClose!=null){
            manager.removeView(imgClose)
        }
    }

}