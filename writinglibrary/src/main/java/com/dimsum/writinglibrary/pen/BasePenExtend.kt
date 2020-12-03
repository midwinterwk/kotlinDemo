package com.dimsum.writinglibrary.pen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BasePenExtend(context: Context) : BasePen() {
    open val TAG = BasePenExtend::class.java.simpleName

    var mHWPointList: ArrayList<ControllerPoint>? = ArrayList<ControllerPoint>()
    var mPointList: ArrayList<ControllerPoint> = ArrayList<ControllerPoint>()
    var mLastPoint: ControllerPoint = ControllerPoint(0F, 0F)
    var mPaint: Paint? = null

    val mUndoStack: ArrayList<ArrayList<ControllerPoint>>? = ArrayList()

    private var mBaseWidth = 0F
    private var mLastVel = 0F
    private var mLastWidth = 0F
    val mBezier = Bezier()

    protected var mCurPoint: ControllerPoint? = null

    private lateinit var mListener: UpdateListener

    fun setPaint(paint: Paint) {
        mPaint = paint
        mBaseWidth = paint.strokeWidth
    }

    override fun draw(canvas: Canvas?) {
        mPaint!!.style = Paint.Style.FILL
        if (mHWPointList == null || mHWPointList!!.size < 1) return

        mCurPoint = mHWPointList!![0]
        drawNeetToDo(canvas)
    }


    override fun onTouchEvent(event: MotionEvent?, canvas: Canvas?): Boolean {
        // event会被下一次事件重用，这里必须生成新的，否则会有问题
        val event2 = MotionEvent.obtain(event)
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                onDown(createMotionElement(event2))
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                onMove(createMotionElement(event2))
                return true
            }
            MotionEvent.ACTION_UP -> {
                onUp(createMotionElement(event2), canvas)
                return true
            }
            else -> {
            }
        }

        return super.onTouchEvent(event, canvas)
    }

    private fun createMotionElement(event: MotionEvent): MotionElement {
        return MotionElement(event.x!!, event.y, event.pressure, event.getToolType(0))
    }

    private fun onDown(mElement: MotionElement) {
        if (mPaint == null) {
            throw NullPointerException("paint 笔不可能为null哦")
        }

        if (getNewPaint(mPaint!!) != null) {
            var paint = getNewPaint(mPaint!!)
            mPaint = paint!!
            //当然了，不要因为担心内存泄漏，在每个变量使用完成后都添加xxx=null，
            // 对于消除过期引用的最好方法，就是让包含该引用的变量结束生命周期，而不是显示的清空
            paint = null
            println("shiming 当绘制的时候是否为新的paint" + mPaint + "原来的对象是否销毁了paint==" + paint)
        }
        mPointList.clear()
        //如果在brush字体这里接受到down的事件，把下面的这个集合清空的话，那么绘制的内容会发生改变
        //不清空的话，也不可能
        mHWPointList!!.clear()
        //记录down的控制点的信息
        val curPoint = ControllerPoint(mElement.x, mElement.y)
        //如果用笔画的画我的屏幕，记录他宽度的和压力值的乘，但是哇，
        mLastWidth = if (mElement.tooltype === MotionEvent.TOOL_TYPE_STYLUS) {
            mElement.pressure * mBaseWidth
        } else {
            //如果是手指画的，我们取他的0.8
            (0.8 * mBaseWidth).toFloat()
        }
        //down下的点的宽度
        curPoint.width = mLastWidth
        mLastVel = 0F
        mPointList.add(curPoint)

        //记录当前的点
        mLastPoint = curPoint

        mHWPointList!!.add(ControllerPoint(curPoint.x, curPoint.y, curPoint.width))
        calcPoint(curPoint)
    }

    private fun onMove(mElement: MotionElement) {
        val curPoint = ControllerPoint(mElement.x, mElement.y)
        val deltaX: Float = curPoint.x - mLastPoint.x
        val deltaY: Float = curPoint.y - mLastPoint.y
        //deltaX和deltay平方和的二次方根 想象一个例子 1+1的平方根为1.4 （x²+y²）开根号
        //同理，当滑动的越快的话，deltaX+deltaY的值越大，这个越大的话，curDis也越大
        val curDis = Math.hypot(deltaX.toDouble(), deltaY.toDouble())
        //我们求出的这个值越小，画的点或者是绘制椭圆形越多，这个值越大的话，绘制的越少，笔就越细，宽度越小
        val curVel: Double = curDis * IPenConfig.DIS_VEL_CAL_FACTOR
        val curWidth: Double
        //点的集合少，我们得必须改变宽度,每次点击的down的时候，这个事件
        if (mPointList.size < 2) {
            curWidth = if (mElement.tooltype === MotionEvent.TOOL_TYPE_STYLUS) {
                (mElement.pressure * mBaseWidth).toDouble()
            } else {
                calcNewWidth(curVel, mLastVel, curDis, 1.5,
                        mLastWidth)
            }
            curPoint.width = curWidth.toFloat()
            mBezier.init(mLastPoint, curPoint)
        } else {
            mLastVel = curVel.toFloat()
            curWidth = if (mElement.tooltype === MotionEvent.TOOL_TYPE_STYLUS) {
                (mElement.pressure * mBaseWidth).toDouble()
            } else {
                //由于我们手机是触屏的手机，滑动的速度也不慢，所以，一般会走到这里来
                //阐明一点，当滑动的速度很快的时候，这个值就越小，越慢就越大，依靠着mlastWidth不断的变换
                calcNewWidth(curVel, mLastVel, curDis, 1.5,
                        mLastWidth)
            }
            curPoint.width = curWidth.toFloat()
            mBezier.addNode(curPoint)
        }
        //每次移动的话，这里赋值新的值
        mLastWidth = curWidth.toFloat()
        mPointList.add(curPoint)
        moveNeetToDo(curDis)

        calcPoint(curPoint)
        mLastPoint = curPoint
    }

    private fun calcNewWidth(curVel: Double, lastVel: Float, curDis: Double, factor: Double, mLastWidth: Float): Double {
        val calVel = curVel * 0.6 + lastVel * (1 - 0.6)
        //返回指定数字的自然对数
        //手指滑动的越快，这个值越小，为负数
        val vfac = Math.log(factor * 2.0f) * -calVel
        //此方法返回值e，其中e是自然对数的基数。
        //Math.exp(vfac) 变化范围为0 到1 当手指没有滑动的时候 这个值为1 当滑动很快的时候无线趋近于0
        //在次说明下，当手指抬起来，这个值会变大，这也就说明，抬起手太慢的话，笔锋效果不太明显
        //这就说明为什么笔锋的效果不太明显
        val calWidth = mBaseWidth * Math.exp(vfac)

        //滑动的速度越快的话，mMoveThres也越大
        var mMoveThres = curDis * 0.01f
        //对之值最大的地方进行控制
        if (mMoveThres > IPenConfig.WIDTH_THRES_MAX) {
            mMoveThres = IPenConfig.WIDTH_THRES_MAX.toDouble()
        }
        return calWidth
    }

    private fun onUp(mElement: MotionElement, canvas: Canvas?) {
        val mCurPoint = ControllerPoint(mElement.x, mElement.y)
        val deltaX: Double = (mCurPoint.x - mLastPoint.x).toDouble()
        val deltaY: Double = (mCurPoint.y - mLastPoint.y).toDouble()
        val curDis = Math.hypot(deltaX, deltaY)
        //如果用笔画的画我的屏幕，记录他宽度的和压力值的乘，但是哇，这个是不会变的
        if (mElement.tooltype === MotionEvent.TOOL_TYPE_STYLUS) {
            mCurPoint!!.width = (mElement.pressure * mBaseWidth)
        } else {
            mCurPoint!!.width = 0F
        }
        mPointList.add(mCurPoint)
        mBezier.addNode(mCurPoint)
        val steps: Int = 1 + curDis.toInt() / IPenConfig.STEPFACTOR
        val step = 1.0 / steps

        mBezier.end()

        if (mPointList.size > 2) {
            var t = 0.0
            while (t < 1.0) {
                val point: ControllerPoint = mBezier.getPoint(t)
                mHWPointList!!.add(point)
                t += step
            }
        }

        mUndoStack!!.add(mHWPointList!!.clone() as java.util.ArrayList<ControllerPoint>)

        // 手指up 我画到纸上上
        draw(canvas)
        //每次抬起手来，就把集合清空，在水彩笔的那个地方，如果啊，我说如果不清空的话，每次抬起手来，
        // 在onDown下去的话，最近画的线的透明度有改变，所以这里clear下线的集合
        clear()
    }

    open fun calcPoint(point: ControllerPoint) {
        if (mLastPoint != point || mHWPointList!!.size < 1) return
        mHWPointList!!.add(ControllerPoint(point.x, point.y, IPenConfig.getPenWidth() * 0.5F, isPoint = true))
        val lastHWPoint = mHWPointList!!.get(mHWPointList!!.lastIndex)
        updatePoint(lastHWPoint.width.toLong().toString())
    }

    fun setUpdateListener(listener: UpdateListener) {
        mListener = listener
    }

    protected fun updatePoint(string: String?) {
        if (mListener != null) {
            mListener.update(string)
        }
    }

    interface UpdateListener {
        fun update(string: String?)
    }

    fun undo(canvas: Canvas?) {
        if (mUndoStack!!.size > 0) {
            mUndoStack!!.removeAt(mUndoStack!!.size - 1)
            mUndoStack.forEach {
                mHWPointList = it
                draw(canvas)
            }
        }

    }

    fun clear() {
        mPointList.clear()
        mHWPointList!!.clear()
    }

    fun clearAll() {
        clear()
        mUndoStack!!.clear()
    }

    private fun getNewPaint(mPaint: Paint): Paint? {
        return null
    }

    abstract fun drawNeetToDo(canvas: Canvas?)

    abstract fun moveNeetToDo(f: Double)

    protected fun drawToPoint(canvas: Canvas, point: ControllerPoint, paint: Paint) {
        if (mCurPoint!!.x == point.x && mCurPoint!!.y == point.y) {
//            drawPoint(canvas, point, paint)
            return
        }
        //水彩笔的效果和钢笔的不太一样，交给自己去实现
        doNeetToDo(canvas, point, paint)
    }

    abstract fun drawPoint(canvas: Canvas, point: ControllerPoint, paint: Paint)

    abstract fun doNeetToDo(canvas: Canvas, point: ControllerPoint, paint: Paint)
}