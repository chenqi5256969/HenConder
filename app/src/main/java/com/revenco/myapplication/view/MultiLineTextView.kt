package com.revenco.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 多行文字测量
 */
class MultiLineTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = 0
) : View(context, attributeSet, def) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}