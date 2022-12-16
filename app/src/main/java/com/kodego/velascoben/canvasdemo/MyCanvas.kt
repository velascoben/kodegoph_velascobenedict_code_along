package com.kodego.velascoben.canvasdemo

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.core.content.res.ResourcesCompat

class MyCanvas (context: Context) : View(context) {

    private lateinit var extraCanvas : Canvas
    private lateinit var extraBitmap : Bitmap

    private val backgroundColor = ResourcesCompat.getColor(resources,R.color.teal_200,null)

    override fun onSizeChanged (w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(::extraBitmap.isInitialized) extraBitmap.recycle()

        extraBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap,0f, 0f,null)
        canvas.drawRect(100f,100f,300f,400f,paint)
        canvas.drawCircle(canvas.width.toFloat()/2,500f,300.0f,paint)
        canvas.drawLine(100f,100f,799f,1000f,paint)
        canvas.drawText("Hello",400f,300f,paint)
    }

    private val paint = Paint().apply {
        color = ResourcesCompat.getColor(resources,R.color.white, null)
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.MITER
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12F
        textSize = 30F
    }

}