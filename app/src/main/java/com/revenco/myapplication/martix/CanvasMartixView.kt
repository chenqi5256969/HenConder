package com.revenco.myapplication.martix

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.revenco.myapplication.R
import com.revenco.myapplication.compressImage
import com.revenco.myapplication.dp2px

/**
 * todo canvas的几何变换
 */
class CanvasMartixView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : View(context, attrs, def){
    private val image_size = 200.dp2px
    val bitmap = compressImage(R.mipmap.icon_desktop, resources, image_size)
    override fun onDraw(canvas: Canvas?) {

    }
}