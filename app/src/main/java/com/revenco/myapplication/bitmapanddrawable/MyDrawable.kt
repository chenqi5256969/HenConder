package com.revenco.myapplication.bitmapanddrawable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.provider.CalendarContract
import com.revenco.myapplication.dp2px

class MyDrawable : Drawable() {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.strokeWidth = 5.dp2px
        it.color = Color.BLUE
    }

    val offsetX = 10.dp2px
    val offsetY = 10.dp2px

    override fun draw(canvas: Canvas) {
        var x = bounds.left.toFloat()
        var y = bounds.top.toFloat()

        while (x <= bounds.right) {
            canvas.drawLine(x, bounds.top.toFloat(), x, bounds.bottom.toFloat(), paint)
            x += offsetX
        }
        while (y <= bounds.bottom) {
            canvas.drawLine(bounds.left.toFloat(),y, bounds.right.toFloat(), y, paint)
            y += offsetY
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun getOpacity(): Int {
        return 1 - paint.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }
}