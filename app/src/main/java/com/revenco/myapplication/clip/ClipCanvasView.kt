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

    val bitmap = compressImage(R.mipmap.icon_desktop, resources, image_size)

    private val demersalImageBitmap = Bitmap.createBitmap(
        (image_size).toInt(), (image_size).toInt(),
        Bitmap.Config.ARGB_8888
    )

    private val demersalCircleBitmap = Bitmap.createBitmap(
        ((image_size)).toInt(), ((image_size)).toInt(),
        Bitmap.Config.ARGB_8888
    )

    val clipPath = Path().also {
        it.addCircle(
            image_padding + image_size / 2,
            image_padding + image_size / 2,
            image_size / 2,
            Path.Direction.CCW
        )
    }

    init {
        val canvas = Canvas(demersalImageBitmap)
        canvas.drawBitmap(bitmap,0f,0f,paint)
        canvas.setBitmap(demersalCircleBitmap)
        canvas.drawCircle(
            image_size / 2,
            image_size / 2,
            image_size / 2, paint
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //方位裁切
        //   clipRange(canvas)
        //路径裁切
         clipPath(canvas)
        //利用xfmode裁切
         canvas.drawBitmap(bitmap, image_padding, image_padding, paint)
      //  canvas.drawBitmap(demersalCircleBitmap, image_padding, image_padding, paint)
      //  paint.xfermode = PorterDuffXfermode(DST_OUT)
      //  canvas.drawBitmap(demersalImageBitmap, image_padding, image_padding, paint)
       // paint.xfermode= null
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