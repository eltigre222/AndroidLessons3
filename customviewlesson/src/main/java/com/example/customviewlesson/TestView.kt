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
    private val startAngle = -180f
    private val colors = listOf(Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW)
    private val sweepAngle = 360f / colors.size

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.GREEN
        paint.strokeWidth = 10f

        paintC.style = Paint.Style.FILL
        paintC.color = Color.GRAY
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircleButton(canvas)
    }

    private fun drawCircleButton(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = width / 2f
        val radius = width.coerceAtMost(height) / 2f
        for (i in colors.indices) {
            paintC.color = colors[i]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + i * sweepAngle,
                sweepAngle,
                true,
                paintC
            )
        }
    }
}