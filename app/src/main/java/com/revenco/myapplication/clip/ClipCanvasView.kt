package com.revenco.myapplication.clip

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.revenco.myapplication.R
import com.revenco.myapplication.compressImage
import com.revenco.myapplication.dp2px

class ClipCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : View(context, attrs, def) {

    private val image_size = 200.dp2px

    private val image_padding = 100.dp2px

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val bitmap = compressImage(R.mipmap.icon_android, resources, image_size)

    val clipPath = Path().also {
        it.addCircle(
            image_padding + image_size / 2,
            image_padding + image_size / 2,
            image_size / 2,
            Path.Direction.CCW
        )
    }

    override fun onDraw(canvas: Canvas) {
        //方位裁切
        //   clipRange(canvas)
        //路径裁切
        //clipPath(canvas)
        //利用xfmode裁切

        canvas.drawBitmap(bitmap, image_padding, image_padding, paint)
    }

    private fun clipPath(canvas: Canvas) {
        canvas.clipPath(clipPath)
    }

    private fun clipRange(canvas: Canvas) {
        canvas.clipRect(
            image_padding,
            image_padding,
            image_padding + image_size / 2,
            image_padding + image_size / 2
        )
    }


}