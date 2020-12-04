package com.dimsum.writinglibrary.pen

import android.content.Context
import android.graphics.*
import android.util.Log
import com.dimsum.writinglibrary.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BrushPen(context: Context?) : BasePenExtend(context!!) {
    override val TAG = BrushPen::class.java.simpleName

    private var mBitmap: Bitmap? = null

    //第一个Rect 代表要绘制的bitmap 区域，
    protected var mOldRect: Rect = Rect()

    //第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
    protected var mNeedDrawRect = RectF()
    protected var mOriginBitmap: Bitmap? = null

    init {
        initTexture()
    }

    /**
     * 由于需要画笔piant中的一些信息，就不能让paint为null，所以setBitmap需要有paint的时候设置
     * @param paint
     */
    override fun setPaint(paint: Paint) {
        super.setPaint(paint)
        mBitmap = makeTintBitmap(mOriginBitmap, mPaint?.color)
        mOldRect.set(0, 0, mBitmap!!.width, mBitmap!!.height)
    }

    private fun initTexture() {
        //通过资源文件生成的原始的bitmap区域 后面的资源图有些更加有意识的东西
        mOriginBitmap = BitmapFactory.decodeResource(
                mContext.getResources(), R.mipmap.spot)
    }


    fun makeTintBitmap(inputBitmap: Bitmap?, tintColor: Int?): Bitmap? {
        if (inputBitmap == null || tintColor == null) {
            return null
        }
        val outputBitmap = Bitmap.createBitmap(inputBitmap.width, inputBitmap.height, inputBitmap.config)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(inputBitmap, 0f, 0f, paint)
        return outputBitmap
    }

    override fun drawNeetToDo(canvas: Canvas?) {
        mBitmap = makeTintBitmap(mOriginBitmap, mPaint?.color)
        mOldRect.set(0, 0, mBitmap!!.width, mBitmap!!.height)
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
            //根据点的信息计算出需要把bitmap绘制在什么地方
            mNeedDrawRect.set((x - w / 2.0f).toFloat(), (y - w / 2.0f).toFloat(),
                    (x + w / 2.0f).toFloat(), (y + w / 2.0f).toFloat())

            //第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
            canvas.drawBitmap(mBitmap!!, mOldRect, mNeedDrawRect, paint)
            x += deltaX
            y += deltaY
            w += deltaW
        }
    }

    override fun drawPoint(canvas: Canvas, point: ControllerPoint, paint: Paint) {
        var x = point.x.toDouble()
        var y = point.y.toDouble()
        val w = point.width.toDouble()
        Log.d(TAG, "w = " + w)

        for (i in 0..2) {
            x++
            y++
            //根据点的信息计算出需要把bitmap绘制在什么地方
            mNeedDrawRect.set((x - w / 2.0f).toFloat(), (y - w / 2.0f).toFloat(),
                    (x + w / 2.0f).toFloat(), (y + w / 2.0f).toFloat())
            //第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
            canvas.drawBitmap(mBitmap!!, mOldRect, mNeedDrawRect, paint)
        }
    }

    override fun calcPoint(point: ControllerPoint) {
        GlobalScope.launch {
            if (mHWPointList!!.size > 2) {
                delay(500)
            } else {
                delay(100)
            }
            if (mLastPoint != point || mHWPointList!!.size < 1) return@launch
            mHWPointList!!.add(ControllerPoint(point.x, point.y, IPenConfig.getPenWidth() * 0.5F, isPoint = true))
            val lastHWPoint = mHWPointList!!.get(mHWPointList!!.lastIndex)
            while (mLastPoint == point && mHWPointList!!.size > 1 && lastHWPoint.width <= IPenConfig.getPenWidth()) {
                lastHWPoint!!.width++
                delay(lastHWPoint!!.width.toLong())
                updatePoint(lastHWPoint.width.toLong().toString())
            }
        }
    }
}
