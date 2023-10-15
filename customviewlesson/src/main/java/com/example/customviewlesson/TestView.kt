package com.example.customviewlesson

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TestView(
    context: Context, attributeSet: AttributeSet
) : View(context, attributeSet) {

    private val paint = Paint()
    private val paintC = Paint()

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.GREEN
        paint.strokeWidth = 10f

        paintC.style = Paint.Style.FILL
        paintC.color = Color.GRAY
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintC)
        }
        if (canvas != null) {
            paint.color = Color.RED
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 100f, paint)
        }
    }
}