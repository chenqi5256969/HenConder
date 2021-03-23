package com.revenco.myapplication.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.revenco.myapplication.dp2px

/**
 * 文字测量的学习
 */
class MeasureTextView constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : View(context, attributeSet, def) {

    private val radius = 100.dp2px
    private val textRect=Rect()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).also {

        it.strokeWidth = 20.dp2px
        it.style = Paint.Style.STROKE

        it.textSize=30.dp2px
        it.textAlign=Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        paint.style=Paint.Style.STROKE
        paint.color = Color.parseColor("#6E6E6E")
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        paint.color = Color.parseColor("#B40431")
        val rectF =
            RectF(width / 2 - radius, height / 2 - radius, radius + width / 2, radius + height / 2)
        canvas.drawArc(rectF, -90f, 220f, false, paint)
        paint.style=Paint.Style.FILL
        paint.getTextBounds("abab",0,"abab".length,textRect)
        canvas.drawText("abab",width/2f,height/2f,paint)
    }

}