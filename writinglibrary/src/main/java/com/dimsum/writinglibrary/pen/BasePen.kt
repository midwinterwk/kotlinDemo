package com.dimsum.writinglibrary.pen

import android.graphics.Canvas
import android.view.MotionEvent

abstract class BasePen {
    abstract fun draw(canvas: Canvas?)

    open fun onTouchEvent(event: MotionEvent?, canvas: Canvas?): Boolean = false
}