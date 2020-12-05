package com.dimsum.writinglibrary.pen

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import com.dimsum.writinglibrary.seal.SealView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DrawPenView : ConstraintLayout {
    private val TAG = "DrawPenView"

    private lateinit var mBgPaint: Paint
    private lateinit var mPaint: Paint
    private var mCanvas: Canvas? = null
    private var mBitmap: Bitmap? = null
    private lateinit var mContext: Context
    var mCanvasCode = IPenConfig.STROKE_TYPE_PEN
    private var mStokeBrushPen: BasePenExtend? = null

    var mCanvasState = 1
    private var mPenconfig = IPenConfig.STROKE_TYPE_BRUSH

    val pathList = ArrayList<String>()

    // 添加缩放功能

    private var point_num = 0 //当前触摸的点数
    private val SCALE_MAX = 8.0f //最大的缩放比例
    private val SCALE_MIN = 1.0f

    private var oldDist = 0.0
    private var moveDist = 0.0

    private var downX = 0.0
    private var downY = 0.0

    private lateinit var mplListener: pathListListener

    private val mListener: BasePenExtend.UpdateListener = object : BasePenExtend.UpdateListener {
        override fun update(width: String?) {
            Log.e(TAG, "point width = " + width)
            postInvalidate()
        }
    }

    private lateinit var mSealView: SealView

    constructor(context: Context) : super(context) {
        initParameter(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initParameter(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initParameter(context)
    }

    private fun initParameter(context: Context) {
        mContext = context
        val dm = DisplayMetrics()
        (mContext as Activity?)!!.windowManager.defaultDisplay.getMetrics(dm)
        mBitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888)
        mStokeBrushPen = SteelPen(context)
        initPaint()
        initCanvas()
    }

    private fun initPaint() {
        mPaint = Paint().apply {
            color = IPenConfig.PEN_COLOUR
            strokeWidth = IPenConfig.getPenWidth()
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND //结束的笔画为圆心
            strokeJoin = Paint.Join.ROUND //连接处元
            alpha = 0xFF
            isAntiAlias = true
            strokeMiter = 1.0f
        }
        mBgPaint = Paint().apply {
            color = IPenConfig.PEN_COLOUR
        }
        mStokeBrushPen!!.setPaint(mPaint!!)
        mStokeBrushPen!!.setUpdateListener(mListener)
    }

    fun resetStrokeWidth() {
        mPaint!!.strokeWidth = IPenConfig.getPenWidth()
        mStokeBrushPen!!.setPaint(mPaint!!)
    }

    fun resetColor(color: Int) {
        IPenConfig.PEN_COLOUR = color
        mPaint!!.color = IPenConfig.PEN_COLOUR
        mStokeBrushPen!!.setPaint(mPaint!!)
    }

    private fun initCanvas() {
        mCanvas = Canvas(mBitmap!!)
        // 设置画布的颜色的问题
        mCanvas!!.drawColor(Color.TRANSPARENT)
    }

    fun resetBackground(bitmap: Bitmap?) {
        if (bitmap == null) {
            setBackgroundColor(Color.WHITE)
            return
        }
        val width = mBitmap!!.width
        val height = mBitmap!!.height
        zoomImg(bitmap, width, height)?.let { this.background = BitmapDrawable(it) }
    }

    fun zoomImg(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mBgPaint)
        when (mCanvasCode) {
            IPenConfig.STROKE_TYPE_PEN, IPenConfig.STROKE_TYPE_BRUSH, IPenConfig.STROKE_TYPE_ERASER -> mStokeBrushPen!!.draw(canvas)
            IPenConfig.STROKE_TYPE_CLEAR -> {
                reset(true)
            }
            IPenConfig.STROKE_TYPE_UNDO -> {
                reset(false)
                mStokeBrushPen!!.undo(mCanvas!!)
            }
            else -> Log.e(TAG, "onDraw" + Integer.toString(mCanvasCode))
        }
        super.onDraw(canvas)
    }


    fun setCanvasCode(canvasCode: Int) {
        mCanvasCode = canvasCode
        when (mCanvasCode) {
            IPenConfig.STROKE_TYPE_PEN -> {
                mStokeBrushPen = SteelPen(mContext)
                mPaint!!.xfermode = null
                setPenconfig(IPenConfig.STROKE_TYPE_PEN)
            }
            IPenConfig.STROKE_TYPE_BRUSH -> {
                mStokeBrushPen = BrushPen(mContext)
                mPaint!!.xfermode = null
                setPenconfig(IPenConfig.STROKE_TYPE_BRUSH)
            }
            IPenConfig.STROKE_TYPE_ERASER -> {
                mStokeBrushPen = Eraser(mContext)
                mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            }
        }
        mCanvasState = 1
        //设置
        if (mStokeBrushPen!!.mPaint == null) {
            mStokeBrushPen!!.setPaint(mPaint!!)
        }
        mStokeBrushPen!!.setUpdateListener(mListener)
        invalidate()
    }

    fun reset(isClearAll: Boolean) {
        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        mCanvas!!.drawPaint(mPaint!!)
        mPaint!!.xfermode = null
        if (isClearAll) {
            mStokeBrushPen!!.clearAll()
        } else {
            mStokeBrushPen!!.clear()
        }
        //这里处理的不太好 需要优化
        mCanvasCode = mPenconfig
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }
        if (mCanvasState == 1) {
            val event2 = MotionEvent.obtain(event)
            mStokeBrushPen!!.onTouchEvent(event2, mCanvas)
            invalidate()
            return true
        } else if(mCanvasState == 3) {
            mSealView.x = event.x - mSealView.right/2
            mSealView.y = event.y - mSealView.bottom/2
            return true
        }
        when (event!!.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                point_num = 1
                downX = event.x.toDouble()
                downY = event.y.toDouble()
            }
            MotionEvent.ACTION_UP -> {
                point_num = 0
                downX = 0.0
                downY = 0.0
            }
            MotionEvent.ACTION_MOVE -> if (point_num == 1) {
                //只有一个手指的时候才有移动的操作
                val lessX = (downX - event.x).toFloat()
                val lessY = (downY - event.y).toFloat()
                setSelfPivot(lessX, lessY)
                //setPivot(getPivotX() + lessX, getPivotY() + lessY);
            } else if (point_num == 2) {
                //只有2个手指的时候才有放大缩小的操作
                moveDist = spacing(event)
                val space: Double = moveDist - oldDist
                val scale = (scaleX + space / width).toFloat()
                if (scale > SCALE_MIN && scale < SCALE_MAX) {
                    setScale(scale)
                } else if (scale < SCALE_MIN) {
                    setScale(SCALE_MIN)
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event) //两点按下时的距离
                point_num += 1
            }
            MotionEvent.ACTION_POINTER_UP -> point_num -= 1
        }
        return true
    }

    //////////////////////////// 缩放 ////////////////////////////
    /**
     * 触摸使用的移动事件
     *
     * @param lessX
     * @param lessY
     */
    private fun setSelfPivot(lessX: Float, lessY: Float) {
        var setPivotX = 0f
        var setPivotY = 0f
        setPivotX = pivotX + lessX
        setPivotY = pivotY + lessY
        Log.e(
            "lawwingLog", "setPivotX:" + setPivotX + "  setPivotY:" + setPivotY
                    + "  getWidth:" + width + "  getHeight:" + height
        )
        if (setPivotX < 0 && setPivotY < 0) {
            setPivotX = 0f
            setPivotY = 0f
        } else if (setPivotX > 0 && setPivotY < 0) {
            setPivotY = 0f
            if (setPivotX > width) {
                setPivotX = width.toFloat()
            }
        } else if (setPivotX < 0 && setPivotY > 0) {
            setPivotX = 0f
            if (setPivotY > height) {
                setPivotY = height.toFloat()
            }
        } else {
            if (setPivotX > width) {
                setPivotX = width.toFloat()
            }
            if (setPivotY > height) {
                setPivotY = height.toFloat()
            }
        }
        setPivot(setPivotX, setPivotY)
    }

    /**
     * 平移画面，当画面的宽或高大于屏幕宽高时，调用此方法进行平移
     *
     * @param x
     * @param y
     */
    fun setPivot(x: Float, y: Float) {
        pivotX = x
        pivotY = y
    }

    /**
     * 计算两个点的距离
     *
     * @param event
     * @return
     */
    private fun spacing(event: MotionEvent): Double {
        return if (event.pointerCount == 2) {
            val x = event.getX(0) - event.getX(1)
            val y = event.getY(0) - event.getY(1)
            Math.sqrt(x * x + y * y.toDouble())
        } else {
            0.0
        }
    }

    /**
     * 设置放大缩小
     *
     * @param scale
     */
    fun setScale(scale: Float) {
        scaleX = scale
        scaleY = scale
    }

    //////////////////////////// 保持作品到本地 ////////////////////////////
    fun getBitmap(): Bitmap? {
        return mBitmap
    }

    fun setPenconfig(config: Int) {
        mPenconfig = config
    }

    private fun createBitmap(): Bitmap? {
        setDrawingCacheEnabled(true)
        buildDrawingCache()
        return getDrawingCache()
    }

    fun saveImge() {
        val fileDir: String = Environment.getExternalStorageDirectory().toString() + "/1/pic/"
        val fileName = System.currentTimeMillis().toString() + ".png"
        val path = fileDir + fileName
        if (!File(fileDir).exists()) {
            File(fileDir).mkdirs()
        }
        bitmap2Path(createBitmap()!!, path)
        setDrawingCacheEnabled(false)
        pathList.add(0, path)
        mplListener.update("add")
        setCanvasCode(IPenConfig.STROKE_TYPE_CLEAR)
        resetBackground(null)
    }

    fun setPathListListener(listener: pathListListener) {
        mplListener = listener
    }

    interface pathListListener {
        fun update(string: String?)
    }

    /**
     * 将bitmap转换为本地的图片
     *
     * @param bitmap
     * @return
     */
    fun bitmap2Path(bitmap: Bitmap, path: String?): String? {
        try {
            val os: OutputStream = FileOutputStream(path)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
        return path
    }

    fun addSealView(sealView: SealView) {
        mSealView = sealView
        addView(sealView)

        val dWidth = this.width
        val dHeight = this.height
        mSealView.x = dWidth * 0.7F
        mSealView.y = dHeight * 0.6F
    }

    fun resetScale(): Boolean {
        setScale(SCALE_MIN)
        return true
    }

    fun resetSeal() {
        removeAllViews()
    }
}