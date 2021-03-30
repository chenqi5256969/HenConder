package com.revenco.myapplication.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.revenco.myapplication.R
import com.revenco.myapplication.compressImage
import com.revenco.myapplication.dp2px

/**
 * 多行文字测量
 */
class MultiLineTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : View(context, attributeSet, def) {

    private var text: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque accumsan pharetra volutpat. Sed sem ipsum, porta vitae quam et, dapibus vulputate purus. Maecenas placerat egestas nisi. Duis fringilla commodo felis vel pharetra. Vestibulum elementum dui vel placerat vestibulum. Maecenas magna libero, consectetur eget felis consequat, pellentesque condimentum nunc. Sed id magna dignissim, placerat libero eget, luctus purus. Suspendisse pretium purus tortor, et pulvinar turpis sagittis vel. Maecenas id nibh et arcu semper accumsan. Etiam sed dui varius, placerat lacus imperdiet, condimentum diam. " +
                "Proin vel velit convallis felis elementum porta. Nam tempus maximus arcu eu bibendum. Nulla facilisi."

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).also {
        it.textSize = 12.dp2px
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textSize = 12.dp2px
    }

    private val imageSize = 100f.dp2px

    private val imageOffsetY = 50f.dp2px

    private val fontMetrics = Paint.FontMetrics()

    private val measuredWidth = floatArrayOf()

    private val bitmap = compressImage(R.mipmap.icon_android, resources, imageSize)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, width - imageSize, imageOffsetY, paint)
        paint.getFontMetrics(fontMetrics)
        //一般的绘制多行文字
        // drawText1(canvas)
        //开始绘制文字
        var start = 0
        var textY = -fontMetrics.top
        var maxWidth = 0f
        while (start < text.length) {
            maxWidth =
                if (textY + fontMetrics.bottom < imageOffsetY || textY + fontMetrics.top > (imageOffsetY + bitmap.height)) {
                    width.toFloat()
                } else {
                    width - imageSize
                }
            val count =
                paint.breakText(text, start, text.length, true, maxWidth, measuredWidth)
            canvas.drawText(text, start, start + count, 0f, textY, paint)
            start += count
            textY += paint.fontSpacing
        }


    }

    private fun drawText1(canvas: Canvas?) {
        val staticLayout =
            StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
        staticLayout.draw(canvas)
    }
}