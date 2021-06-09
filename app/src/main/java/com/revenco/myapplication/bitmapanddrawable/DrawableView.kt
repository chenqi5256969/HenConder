package com.revenco.myapplication.bitmapanddrawable

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

//自定义drawable
class DrawableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : View(context, attributeSet, def) {

    private val myDrawable = MyDrawable()

    override fun onDraw(canvas: Canvas) {
        myDrawable.setBounds(0, 0, 300, 300)
        myDrawable.draw(canvas)
    }

}