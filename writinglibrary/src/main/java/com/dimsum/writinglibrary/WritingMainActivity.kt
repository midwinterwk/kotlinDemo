package com.dimsum.writinglibrary

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimsum.writinglibrary.pen.DrawPenView
import com.dimsum.writinglibrary.pen.IPenConfig
import kotlinx.android.synthetic.main.activity_writing_main.*
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver


class WritingMainActivity : AppCompatActivity() {

    private lateinit var popup: ColorPickerPopup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_main)
        btn_brush.setOnClickListener(this::onClick)
        btn_pen.setOnClickListener(this::onClick)
        btn_undo.setOnClickListener(this::onClick)
        btn_clear.setOnClickListener(this::onClick)
        btn_color.setOnClickListener(this::onClick)
        btn_save.setOnClickListener(this::onClick)
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)

        popup = ColorPickerPopup.Builder(this)
                .initialColor(Color.RED)
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("确定")
                .cancelTitle("取消")
                .showIndicator(true)
                .showValue(true)
                .build()
    }

    override fun onResume() {
        super.onResume()
        drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_BRUSH)
        setStrokeWidth(60)
        rv_show.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        rv_show.adapter = BitmapAdapter(this, drawPenView.pathList)
        rv_show.addItemDecoration(object :RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                outRect.right = 1
            }
        })

        drawPenView.setPathListListener(object :DrawPenView.pathListListener {
            override fun update(string: String?) {
                rv_show?.adapter?.notifyDataSetChanged()
            }
        })
    }

    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_brush -> {
                drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_BRUSH)
                setStrokeWidth(60)
            }
            R.id.btn_pen -> {
                drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_PEN)
                setStrokeWidth(10)
            }
            R.id.btn_undo -> drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_UNDO)
            R.id.btn_clear -> drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_ERASER)
            R.id.btn_save -> drawPenView.saveImge()
            R.id.btn_color ->
                popup.show(drawPenView, object : ColorPickerObserver {
                    override fun onColorPicked(color: Int) {
                        drawPenView.resetColor(color)
                    }
                    override fun onColor(color: Int, fromUser: Boolean) {}
                })
        }
    }

    private fun setStrokeWidth(progress: Int) {
        seekBar.setProgress(progress)
        tv_progress.setText(IPenConfig.getPenWidth(progress).toString())
        drawPenView.resetStrokeWidth()
    }

    private val onSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            tv_progress.setText(IPenConfig.getPenWidth(progress).toString())
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            Log.e("onSeekBarChangeListener", "progress="+seekBar.progress)
            drawPenView.resetStrokeWidth()
        }
    }


}