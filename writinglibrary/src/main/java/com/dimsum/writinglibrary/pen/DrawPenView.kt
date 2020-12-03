package com.dimsum.writinglibrary.pen

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Environment
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DrawPenView : View {
    private val TAG = "DrawPenView"

    private lateinit var mPaint: Paint
    private var mCanvas: Canvas? = null
    var mBitmap: Bitmap? = null
    private lateinit var mContext: Context
    var mCanvasCode = IPenConfig.STROKE_TYPE_PEN
    private var mStokeBrushPen: BasePenExtend? = null

    private var mIsCanvasDraw = false
    private var mPenconfig = IPenConfig.STROKE_TYPE_BRUSH

    val pathList = ArrayList<String>()

    private lateinit var mplListener: pathListListener

    private val mListener: BasePenExtend.UpdateListener = object : BasePenExtend.UpdateListener {
        override fun update(width: String?) {
            Log.e(TAG, "point width = " + width)
            postInvalidate()
        }
    }

    constructor(context: Context) : super(context) {
        initParameter(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initParameter(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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
        mPaint = Paint()
        mPaint!!.color = IPenConfig.PEN_CORLOUR
        mPaint!!.strokeWidth = IPenConfig.getPenWidth()
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeCap = Paint.Cap.ROUND //结束的笔画为圆心
        mPaint!!.strokeJoin = Paint.Join.ROUND //连接处元
        mPaint!!.alpha = 0xFF
        mPaint!!.isAntiAlias = true
        mPaint!!.strokeMiter = 1.0f
        mStokeBrushPen!!.setPaint(mPaint!!)

        mStokeBrushPen!!.setUpdateListener(mListener)
    }

    fun resetStrokeWidth() {
        mPaint!!.strokeWidth = IPenConfig.getPenWidth()
        mStokeBrushPen!!.setPaint(mPaint!!)
    }

    fun resetColor(color: Int) {
        IPenConfig.PEN_CORLOUR = color
        mPaint!!.color = IPenConfig.PEN_CORLOUR
        mStokeBrushPen!!.setPaint(mPaint!!)
    }

    private fun initCanvas() {
        mCanvas = Canvas(mBitmap!!)
        //设置画布的颜色的问题
        mCanvas!!.drawColor(Color.TRANSPARENT)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mPaint)
        when (mCanvasCode) {
            IPenConfig.STROKE_TYPE_PEN, IPenConfig.STROKE_TYPE_BRUSH -> mStokeBrushPen!!.draw(canvas)
            IPenConfig.STROKE_TYPE_ERASER -> {
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
                setPenconfig(IPenConfig.STROKE_TYPE_PEN)
            }
            IPenConfig.STROKE_TYPE_BRUSH -> {
                mStokeBrushPen = BrushPen(mContext)
                setPenconfig(IPenConfig.STROKE_TYPE_BRUSH)
            }
        }
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
        mIsCanvasDraw = false
        if (isClearAll) {
            mStokeBrushPen!!.clearAll()
        } else {
            mStokeBrushPen!!.clear()
        }
        //这里处理的不太好 需要优化
        mCanvasCode = mPenconfig
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mIsCanvasDraw = true
        val event2 = MotionEvent.obtain(event)
        mStokeBrushPen!!.onTouchEvent(event2, mCanvas)
        invalidate()
        return true
    }

    fun getBitmap(): Bitmap? {
        return mBitmap
    }

    fun setPenconfig(config: Int) {
        mPenconfig = config
    }

    fun saveImge() {
        val fileDir: String = Environment.getExternalStorageDirectory().toString() + "/1/pic/"
        val fileName = System.currentTimeMillis().toString() + ".png"
        val path = fileDir + fileName
        if (!File(fileDir).exists()) {
            File(fileDir).mkdirs()
        }
        bitmap2Path(getBitmap()!!, path)
        pathList.add(0, path)
        mplListener.update("add")
        setCanvasCode(IPenConfig.STROKE_TYPE_ERASER)
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
}