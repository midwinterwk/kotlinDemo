package com.dimsum.writinglibrary.pen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author shiming
 * @version v1.0 create at 2017/10/17
 * @des 钢笔
 */
class SteelPen(context: Context?) : BasePenExtend(context!!) {
    override val TAG = SteelPen::class.java.simpleName

    override fun drawNeetToDo(canvas: Canvas?) {
        for (i in 0 until mHWPointList!!.size) {
            val point = mHWPointList!![i]
            if (point.isPoint) {
                drawPoint(canvas!!, point, mPaint!!)
            } else {
                drawToPoint(canvas!!, point, mPaint!!)
            }
            mCurPoint = point
        }
    }

    override fun moveNeetToDo(curDis: Double) {
        val steps: Int = 1 + curDis.toInt() / IPenConfig.STEPFACTOR
        val step = 1.0 / steps
        var t = 0.0
        while (t < 1.0) {
            val point = mBezier.getPoint(t)
            mHWPointList!!.add(point)
            t += step
        }
    }

    override fun doNeetToDo(canvas: Canvas, point: ControllerPoint, paint: Paint) {
        drawLine(canvas, mCurPoint!!.x.toDouble(), mCurPoint!!.y.toDouble(), mCurPoint!!.width.toDouble(), point.x.toDouble(),
                point.y.toDouble(), point.width.toDouble(), paint)
    }

    /**
     * 其实这里才是关键的地方，通过画布画椭圆，每一个点都是一个椭圆，这个椭圆的所有细节，逐渐构建出一个完美的笔尖
     * 和笔锋的效果,我觉得在这里需要大量的测试，其实就对低端手机进行排查，看我们绘制的笔的宽度是多少，绘制多少个椭圆
     * 然后在低端手机上不会那么卡，当然你哪一个N年前的手机给我，那也的卡，只不过需要适中的范围里面
     *
     * @param canvas
     * @param x0
     * @param y0
     * @param w0
     * @param x1
     * @param y1
     * @param w1
     * @param paint
     */
    private fun drawLine(canvas: Canvas, x0: Double, y0: Double, w0: Double, x1: Double, y1: Double, w1: Double, paint: Paint) {
        //求两个数字的平方根 x的平方+y的平方在开方记得X的平方+y的平方=1，这就是一个园
        val curDis = Math.hypot(x0 - x1, y0 - y1)
        var steps = 1
        steps = if (paint.strokeWidth < 6) {
            1 + (curDis / 2).toInt()
        } else if (paint.strokeWidth > 60) {
            1 + (curDis / 4).toInt()
        } else {
            1 + (curDis / 3).toInt()
        }
        val deltaX = (x1 - x0) / steps
        val deltaY = (y1 - y0) / steps
        val deltaW = (w1 - w0) / steps
        var x = x0
        var y = y0
        var w = w0
        for (i in 0 until steps) {
            //都是用于表示坐标系中的一块矩形区域，并可以对其做一些简单操作
            //精度不一样。Rect是使用int类型作为数值，RectF是使用float类型作为数值。
            //            Rect rect = new Rect();
            val oval = RectF()
            oval.set((x - w / 4.0f).toFloat(), (y - w / 2.0f).toFloat(), (x + w / 4.0f).toFloat(), (y + w / 2.0f).toFloat())
            //最基本的实现，通过点控制线，绘制椭圆
            canvas.drawOval(oval, paint)
            x += deltaX
            y += deltaY
            w += deltaW
        }
    }

    override fun drawPoint(canvas: Canvas, point: ControllerPoint, paint: Paint) {
        val x = point.x.toDouble()
        val y = point.y.toDouble()
        val w = point.width.toDouble()
        Log.d(TAG, "w = " + w)
        val oval = RectF()
        oval.set((x - w / 4.0f).toFloat(), (y - w / 2.0f).toFloat(), (x + w / 4.0f).toFloat(), (y + w / 2.0f).toFloat())
        canvas.drawOval(oval, paint)
    }
}