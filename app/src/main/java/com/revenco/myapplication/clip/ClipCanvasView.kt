package com.revenco.myapplication.clip

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.PorterDuff.Mode.*
import android.util.AttributeSet
import android.view.View
import com.revenco.myapplication.R
import com.revenco.myapplication.compressImage
import com.revenco.myapplication.dp2px

/**
 * canvas的裁剪clip
 */
class ClipCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : View(context, attrs, def) {

    private val image_size = 200.dp2px

    private val image_padding = 100.dp2px

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val rectF=RectF(0f,0f,image_size,image_size)

    val bitmap = compressImage(R.mipmap.icon_desktop, resources, image_size)

    private val demersalImageBitmap = Bitmap.createBitmap(
        (image_size).toInt(),
        (image_size).toInt(),
        Bitmap.Config.ARGB_8888
    )

    private val demersalCircleBitmap = Bitmap.createBitmap(
        (image_size).toInt(),
        (image_size).toInt(),
        Bitmap.Config.ARGB_8888
    )

    private val clipPath = Path().also {
        it.addCircle(
            image_padding + image_size / 2,
            image_padding + image_size / 2,
            image_size / 2,
            Path.Direction.CCW
        )
    }

    private val xfermode= PorterDuffXfermode(SRC_IN)

    init {
        val canvas = Canvas(demersalImageBitmap)

        canvas.drawBitmap(
         bitmap,0f,0f,paint
        )
        canvas.setBitmap(demersalCircleBitmap)
        canvas.drawCircle(
            (bitmap.width/4).toFloat(),
            (bitmap.width/4).toFloat(),
            (bitmap.width/4).toFloat(),paint
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //方位裁切
        //    clipRange(canvas)
        //路径裁切
        // clipPath(canvas)
        //利用xfmode裁切
        val saveLayerCount = canvas.saveLayer(rectF, paint)
        canvas.drawBitmap(demersalCircleBitmap, 0f, 0f, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(demersalImageBitmap, 0f, 0f, paint)
        paint.xfermode = null
        canvas.restoreToCount(saveLayerCount)
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