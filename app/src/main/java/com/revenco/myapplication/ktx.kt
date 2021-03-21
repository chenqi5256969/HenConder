package com.revenco.myapplication

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import androidx.annotation.DrawableRes


fun compressImage(@DrawableRes srcId: Int, resources: Resources, width: Float): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, srcId, options)
    options.inTargetDensity = width.toInt()
    options.inDensity = options.outWidth
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(resources, srcId, options)
}

val Float.dp2px: Float
    get() {
        return TypedValue.applyDimension(
            COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        )
    }

fun log(msg:String)
{
    Log.i("ScaleableImageView--->",msg)
}