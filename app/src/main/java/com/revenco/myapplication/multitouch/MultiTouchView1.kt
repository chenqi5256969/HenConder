package com.revenco.myapplication.multitouch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.revenco.myapplication.R
import com.revenco.myapplication.compressImage
import com.revenco.myapplication.dp2px

/**
 * 多手指移动图片
 */
class MultiTouchView1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : View(context, attrs, def) {

    private val bm: Bitmap by lazy {
        compressImage(R.mipmap.icon_android, resources, 200f.dp2px)
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var downX = 0f
    private var downY = 0f

    private var moveX = 0f
    private var moveY = 0f

    private var originalX = 0f
    private var originalY = 0f

    private var pointerId = 0


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bm, moveX, moveY, paint)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                //单个手指按下，index就是0
                pointerId = event.getPointerId(0)
                downX = event.getX(0)
                downY = event.getY(0)

                originalX = moveX
                originalY = moveY
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                pointerId = event.getPointerId(actionIndex)

                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)

                originalX = moveX
                originalY = moveY
            }

            //抬起之后，交给另外一个手指接管
            MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val upPointerId = event.getPointerId(actionIndex)
                //抬起的这根手指正好是滑动的手指
                if (upPointerId == pointerId) {
                    val newIndex = if (actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    pointerId = event.getPointerId(newIndex)

                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)

                    originalX = moveX
                    originalY = moveY
                }
            }


            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = event.findPointerIndex(pointerId)
                moveX = (event.getX(pointerIndex) - downX) + originalX
                moveY = (event.getY(pointerIndex) - downY) + originalY
                invalidate()
            }
        }

        return true
    }
}