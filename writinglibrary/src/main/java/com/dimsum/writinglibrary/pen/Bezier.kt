package com.dimsum.writinglibrary.pen


/**
 * @author shiming
 * @version v1.0 create at 2017/8/24
 * @des  对点的位置和宽度控制的bezier曲线，主要是两个点，都包含了宽度和点的坐标
 */
class Bezier {
    //控制点的，
    private var mControl = ControllerPoint()

    //距离
    private var mDestination = ControllerPoint()

    //下一个需要控制点
    private val mNextControl = ControllerPoint()

    //资源的点
    private var mSource = ControllerPoint()

    /**
     * 初始化两个点，
     * @param last 最后的点的信息
     * @param cur 当前点的信息,当前点的信息，当前点的是根据事件获得，同时这个当前点的宽度是经过计算的得出的
     */
    fun init(last: ControllerPoint, cur: ControllerPoint) {
        init(last.x, last.y, last.width, cur.x, cur.y, cur.width)
    }

    fun init(lastx: Float, lasty: Float, lastWidth: Float, x: Float, y: Float, width: Float) {
        //资源点设置，最后的点的为资源点
        mSource.x = lastx
        mSource.y = lasty
        mSource.width = lastWidth
        val xmid = getMid(lastx, x)
        val ymid = getMid(lasty, y)
        val wmid = getMid(lastWidth, width)
        //距离点为平均点
        mDestination.x= xmid
        mDestination.y = ymid
        mDestination.width = wmid
        //控制点为当前的距离点
        mControl.x = getMid(lastx, xmid)
        mControl.y = getMid(lasty, ymid)
        mControl.width = getMid(lastWidth, wmid)
        //下个控制点为当前点
        mNextControl.x = x
        mNextControl.y = y
        mNextControl.width = width
    }

    fun addNode(cur: ControllerPoint) {
        addNode(cur.x, cur.y, cur.width)
    }

    /**
     * 替换旧的点，原来的距离点变换为资源点，控制点变为原来的下一个控制点，距离点取原来控制点的和新的的一半
     * 下个控制点为新的点
     * @param x 新的点的坐标
     * @param y 新的点的坐标
     * @param width
     */
    fun addNode(x: Float, y: Float, width: Float) {
        mSource = mDestination.copy()
        mControl = mNextControl.copy()
        mDestination.x = getMid(mNextControl.x, x)
        mDestination.y = getMid(mNextControl.y, y)
        mDestination.width = getMid(mNextControl.width, width)
        mNextControl.x = x
        mNextControl.y = y
        mNextControl.width = width
    }

    /**
     * 结合手指抬起来的动作，告诉现在的曲线控制点也必须变化，其实在这里也不需要结合着up事件使用
     * 因为在down的事件中，所有点都会被重置，然后设置这个没有多少意义，但是可以改变下个事件的朝向改变
     * 先留着，因为后面如果需要控制整个颜色的改变的话，我的依靠这个方法，还有按压的时间的变化
     */
    fun end() {
        mSource = mDestination.copy()
        val x = getMid(mNextControl.x, mSource.x)
        val y = getMid(mNextControl.y, mSource.y)
        val w = getMid(mNextControl.width, mSource.width)
        mControl = mControl.copy(x = x, y = y, width = w)
        mDestination = mNextControl.copy()
    }

    /**
     *
     * @param t 孔子
     * @return
     */
    fun getPoint(t: Double): ControllerPoint {
        val x = getX(t).toFloat()
        val y = getY(t).toFloat()
        val w = getW(t).toFloat()
        val point = ControllerPoint(x, y, w)
        return point
    }

    /**
     * 三阶曲线的控制点
     * @param p0
     * @param p1
     * @param p2
     * @param t
     * @return
     */
    private fun getValue(p0: Double, p1: Double, p2: Double, t: Double): Double {
        val A = p2 - 2 * p1 + p0
        val B = 2 * (p1 - p0)
        return A * t * t + B * t + p0
    }

    private fun getX(t: Double): Double {
        return getValue(mSource.x.toDouble(), mControl.x.toDouble(), mDestination.x.toDouble(), t)
    }

    private fun getY(t: Double): Double {
        return getValue(mSource.y.toDouble(), mControl.y.toDouble(), mDestination.y.toDouble(), t)
    }

    private fun getW(t: Double): Double {
        return getWidth(mSource.width.toDouble(), mDestination.width.toDouble(), t)
    }

    /**
     *
     * @param x1 一个点的x
     * @param x2 一个点的x
     * @return
     */
    private fun getMid(x1: Float, x2: Float): Float {
        return ((x1 + x2) / 2.0).toFloat()
    }

    private fun getWidth(w0: Double, w1: Double, t: Double): Double {
        return w0 + (w1 - w0) * t
    }
}