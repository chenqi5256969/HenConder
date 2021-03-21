package com.revenco.myapplication.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PathMeasure
import android.os.Build
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.GestureDetectorCompat
import com.revenco.myapplication.R
import com.revenco.myapplication.compressImage
import com.revenco.myapplication.dp2px
import com.revenco.myapplication.log
import kotlin.math.max
import kotlin.math.min


val IMAGE_SIZE = 200f.dp2px

/**
 * 手指缩放图片
 */
class ScaleableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : View(context, attrs, def) {


    private val bitmap = compressImage(R.mipmap.icon_android, resources, IMAGE_SIZE)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var left = 0f
    private var top = 0f

    private var smallScale = 0f
    private var bigScale = 0f

    private var currentScale = 0f

    private val SCALE_TRANS = 1.5f

    private var isShowBigImage = false

    private var offsetX: Float = 0f
    private var offsetY: Float = 0f


    private val gestureDetectorCompat = GestureDetectorCompat(context, SBGesture())
    private val scaleGestureDetector = ScaleGestureDetector(context, SBScaleGesture())

    private val scaleAnimator = ValueAnimator.ofFloat(smallScale, bigScale).apply {
        duration = 800
        addUpdateListener { animation ->
            currentScale = animation.animatedValue as Float
            invalidate()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            return gestureDetectorCompat.onTouchEvent(event)
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        val bWidth = bitmap.width
        val bHeight = bitmap.height

        left = ((width - bWidth) / 2).toFloat()
        top = ((height - bHeight) / 2).toFloat()

        if (bWidth / bHeight > width / height) {
            //说明图片是一个横向显示的图片
            smallScale = width / bWidth.toFloat()
            bigScale = (height / bHeight.toFloat()) * SCALE_TRANS
        } else {
            smallScale = height / bHeight.toFloat()
            bigScale = (width / bWidth.toFloat()) * SCALE_TRANS
        }

        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(
            offsetX * (scaleFraction),
            offsetY * (scaleFraction)
        )
        canvas.scale(currentScale, currentScale, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawBitmap(
            bitmap,
            left,
            top,
            paint
        )
    }


    private fun maxOffset() {
        offsetX = min(offsetX, ((bitmap.width) * bigScale - width) / 2)
        offsetX = max(offsetX, -((bitmap.width) * bigScale - width) / 2)
        offsetY = min(offsetY, ((bitmap.height) * bigScale - height) / 2)
        offsetY = max(offsetY, -((bitmap.height) * bigScale - height) / 2)
    }

    inner class SBScaleGesture : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = (detector.focusX - width / 2) / (1 - bigScale / smallScale)
            offsetY = (detector.focusY - height / 2) / (1 - bigScale / smallScale)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            currentScale *= (detector.scaleFactor)
            invalidate()
            return true
        }
    }

    inner class SBGesture : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            log(".....touch......")
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (isShowBigImage) {
                offsetX += -distanceX
                offsetY += -distanceY
                //设置不能超出的距离
                maxOffset()
                invalidate()
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isShowBigImage = !isShowBigImage
            if (isShowBigImage) {
                scaleAnimator.start()
                offsetX = -(e.x - width / 2) * (bigScale / smallScale - 1)
                offsetY = -(e.y - height / 2) * (bigScale / smallScale - 1)
                maxOffset()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }
    }
}