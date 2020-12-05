package com.dimsum.writinglibrary

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimsum.writinglibrary.pen.DrawPenView
import com.dimsum.writinglibrary.pen.IPenConfig
import com.dimsum.writinglibrary.seal.SealView
import kotlinx.android.synthetic.main.activity_writing_main.*
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver


class WritingMainActivity : AppCompatActivity() {

    private lateinit var popup: ColorPickerPopup
    private var isSettingShow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_main)
        btn_brush.setOnClickListener(this::onClick)
        btn_pen.setOnClickListener(this::onClick)
        btn_eraser.setOnClickListener(this::onClick)
        btn_undo.setOnClickListener(this::onClick)
        btn_color.setOnClickListener(this::onClick)
        btn_save.setOnClickListener(this::onClick)
        btn_scale.setOnClickListener(this::onClick)
        btn_background.setOnClickListener(this::onClick)
        btn_seal.setOnClickListener(this::onClick)
        btn_sure.setOnClickListener(this::onClick)
        btn_cancel.setOnClickListener(this::onClick)

        btn_undo.setOnLongClickListener { drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_CLEAR); true }
        btn_color.setOnLongClickListener {
            drawPenView.resetColor(Color.BLACK)
            Toast.makeText(this, R.string.resetBlack, Toast.LENGTH_LONG).show()
            true
        }
        btn_scale.setOnLongClickListener { drawPenView.resetScale() }
        btn_background.setOnLongClickListener {
            drawPenView.resetBackground(null)
            true
        }
        btn_seal.setOnLongClickListener { drawPenView.resetSeal(); true }

        iv_setting.setOnClickListener(this::onClick)
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)

//        colorPicker.subscribe { color, _ -> drawPenView.resetColor(color)}
        popup = ColorPickerPopup.Builder(this)
            .initialColor(IPenConfig.PEN_COLOUR)
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
        rv_show.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        rv_show.adapter = BitmapAdapter(this, drawPenView.pathList)
        rv_show.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                outRect.right = 1
            }
        })

        drawPenView.setPathListListener(object : DrawPenView.pathListListener {
            override fun update(string: String?) {
                rv_show?.adapter?.notifyDataSetChanged()
            }
        })
    }

    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_setting -> {
                rotate(v)
                if (isSettingShow) {
                    fadeOut(sv_setting)
                    fadeOut(rv_show)
                    v.setBackgroundResource(R.drawable.ic_add_60)
                    isSettingShow = false
                } else {
                    fadeIn(sv_setting)
                    fadeIn(rv_show)
                    v.setBackgroundResource(R.drawable.ic_close_60)
                    isSettingShow = true
                }
            }
            R.id.btn_brush -> {
                drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_BRUSH)
                setStrokeWidth(60)
            }
            R.id.btn_pen -> {
                drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_PEN)
                setStrokeWidth(10)
            }
            R.id.btn_eraser -> drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_ERASER)
            R.id.btn_undo -> drawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_UNDO)
            R.id.btn_save -> drawPenView.saveImge()
            R.id.btn_color ->
                popup.show(drawPenView, object : ColorPickerObserver {
                    override fun onColorPicked(color: Int) {
                        drawPenView.resetColor(color)
                    }

                    override fun onColor(color: Int, fromUser: Boolean) {
                        drawPenView.resetColor(color)
                    }
                })
            R.id.btn_scale -> drawPenView.mCanvasState = 2
            R.id.btn_background -> {
                val intent = Intent(Intent.ACTION_PICK, null)
                intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                startActivityForResult(intent, 0x1)
            }
            R.id.btn_seal -> {
                cl_seal.visibility = View.VISIBLE
            }
            R.id.btn_sure -> {
                drawPenView.mCanvasState = 3
                var txt = edit_seal.text.toString()
                if (txt.trim().length > 2) {
                    txt = txt.substring(0, 2)
                }
                val sealView = SealView(this).apply { addText(txt) }
                drawPenView.addSealView(sealView)
                cl_seal.visibility = View.GONE
            }
            R.id.btn_cancel -> {
                cl_seal.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0x1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val bitmap =
                    BitmapFactory.decodeStream(contentResolver.openInputStream(data.data!!))
                drawPenView.resetBackground(bitmap)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
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
            Log.e("onSeekBarChangeListener", "progress=" + seekBar.progress)
            drawPenView.resetStrokeWidth()
        }
    }


    private fun fadeIn(view: View, startAlpha: Float, endAlpha: Float, duration: Long) {
        if (view.visibility == View.VISIBLE) return
        view.visibility = View.VISIBLE
        val animation: Animation = AlphaAnimation(startAlpha, endAlpha)
        animation.duration = duration
        view.startAnimation(animation)
    }

    private fun fadeIn(view: View) {
        fadeIn(view, 0f, 1f, 400)
        view.isEnabled = true
    }

    private fun fadeOut(view: View) {
        if (view.visibility != View.VISIBLE) return

        view.isEnabled = false
        val animation: Animation = AlphaAnimation(1f, 0f)
        animation.duration = 400
        view.startAnimation(animation)
        view.visibility = View.GONE
    }

    private fun rotate(view: View) {
        val animation: Animation = RotateAnimation(
            0f,
            90f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 400
        view.startAnimation(animation)
    }

}