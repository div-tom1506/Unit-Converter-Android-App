package com.divyanshu.unitconverter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class AnalogClockView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val calendar = Calendar.getInstance()
    private var timeZone: TimeZone = TimeZone.getDefault()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2
        val centerY = height / 2
        val radius = (width.coerceAtMost(height) / 2.5).toFloat()

        drawClockFace(canvas, centerX, centerY, radius)

        calendar.timeZone = timeZone
        calendar.time = Date()

        val hours = calendar.get(Calendar.HOUR)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        drawHand(
            canvas,
            centerX,
            centerY,
            radius * 0.5f,
            (hours % 12) * 30f + (minutes / 2f),
            Color.BLACK,
            10f
        ) // Hour hand
        drawHand(
            canvas,
            centerX,
            centerY,
            radius * 0.7f,
            minutes * 6f,
            Color.BLUE,
            8f
        ) // Minute hand
        drawHand(
            canvas,
            centerX,
            centerY,
            radius * 0.85f,
            seconds * 6f,
            Color.RED,
            4f,
            true
        ) // Second hand with smooth effect

        postInvalidateDelayed(1000)
    }

    private fun drawClockFace(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            setShadowLayer(15f, 0f, 0f, Color.GRAY)  // 3D Effect
        }
        canvas.drawCircle(cx, cy, radius, bgPaint)

        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }
        canvas.drawCircle(cx, cy, radius, borderPaint)

        val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            strokeWidth = 6f
        }

        val smallMarkerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.DKGRAY
            strokeWidth = 3f
        }

        for (i in 0..59) {
            val angle = Math.toRadians((i * 6).toDouble()).toFloat()
            val startX =
                cx + (radius * if (i % 5 == 0) 0.9f else 0.95f) * Math.cos(angle.toDouble())
                    .toFloat()
            val startY =
                cy + (radius * if (i % 5 == 0) 0.9f else 0.95f) * Math.sin(angle.toDouble())
                    .toFloat()
            val endX = cx + (radius * Math.cos(angle.toDouble())).toFloat()
            val endY = cy + (radius * Math.sin(angle.toDouble())).toFloat()

            canvas.drawLine(
                startX,
                startY,
                endX,
                endY,
                if (i % 5 == 0) markerPaint else smallMarkerPaint
            )
        }
    }

    private fun drawHand(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        length: Float,
        angle: Float,
        color: Int,
        strokeWidth: Float,
        smooth: Boolean = false
    ) {
        val radians = Math.toRadians(angle.toDouble() - 90)
        val endX = cx + (length * Math.cos(radians)).toFloat()
        val endY = cy + (length * Math.sin(radians)).toFloat()

        val handPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = color
            this.strokeWidth = strokeWidth
            strokeCap = if (smooth) Paint.Cap.ROUND else Paint.Cap.BUTT
        }
        canvas.drawLine(cx, cy, endX, endY, handPaint)
    }

    fun setTimeZone(timeZoneId: String) {
        this.timeZone = TimeZone.getTimeZone(timeZoneId)
        invalidate()
    }
}
