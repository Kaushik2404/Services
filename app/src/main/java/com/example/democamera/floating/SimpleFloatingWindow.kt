package com.example.democamera.floating

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.democamera.ui.MainActivity
import com.example.democamera.R
import java.lang.Math.abs

class SimpleFloatingWindow constructor(private val context: Context) {

    private var windowManager: WindowManager? = null
        get() {
            if (field == null) field = (context.getSystemService(WINDOW_SERVICE) as WindowManager)
            return field
        }

    private var floatView: View =
        LayoutInflater.from(context).inflate(R.layout.layout_floating_window, null)

    private lateinit var layoutParams: WindowManager.LayoutParams

    var textView:AppCompatTextView= floatView.findViewById(R.id.textView)
    var btnopen:Button= floatView.findViewById(R.id.btnopen)
     var closeImageButton:AppCompatImageButton=floatView.findViewById(R.id.closeImageButton)

    private var lastX: Int = 0
    private var lastY: Int = 0
    private var firstX: Int = 0
    private var firstY: Int = 0

    private var isShowing = false
    private var touchConsumedByMove = false

    private val onTouchListener = View.OnTouchListener { view, event ->
        val totalDeltaX = lastX - firstX
        val totalDeltaY = lastY - firstY

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
                firstX = lastX
                firstY = lastY
            }
            MotionEvent.ACTION_UP -> {
                view.performClick()
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.rawX.toInt() - lastX
                val deltaY = event.rawY.toInt() - lastY
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
                if (abs(totalDeltaX) >= 5 || abs(totalDeltaY) >= 5) {
                    if (event.pointerCount == 1) {
                        layoutParams.x += deltaX
                        layoutParams.y += deltaY
                        touchConsumedByMove = true
                        windowManager?.apply {
                            updateViewLayout(floatView, layoutParams)
                        }
                    } else {
                        touchConsumedByMove = false
                    }
                } else {
                    touchConsumedByMove = false
                }
            }
            else -> {
            }
        }
        touchConsumedByMove
    }

    init {
        with(floatView) {
            closeImageButton.setOnClickListener { dismiss() }
            textView.text = "Helloo.."

            btnopen.setOnClickListener {
                val intent = Intent ( context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }

        }

        floatView.setOnTouchListener(onTouchListener)
//        floatView.setOnClickListener {

//        }

        layoutParams = WindowManager.LayoutParams().apply {
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            @Suppress("DEPRECATION")
            type = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else -> WindowManager.LayoutParams.TYPE_TOAST
            }

            gravity = Gravity.CENTER
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
    }

    fun show() {
        if (context.canDrawOverlays) {
            dismiss()
            isShowing = true
            windowManager?.addView(floatView, layoutParams)
        }
    }

    fun dismiss() {
        if (isShowing) {
            windowManager?.removeView(floatView)
            isShowing = false
        }
    }
}