package com.dimsum.writinglibrary.seal

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.dimsum.writinglibrary.R
import kotlinx.android.synthetic.main.seal_text.view.*


class SealView : FrameLayout {

    private lateinit var mContext: Context

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
        layoutParams = LayoutParams(-2,-2)
        setType(null)
    }

    fun setType(sealType: Int?) {
        (mContext as Activity).layoutInflater.inflate(sealType ?: R.layout.seal_text, null)?.let {
            addView(it)
        }
    }

    fun addText(txt: String) {
//      setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/xiaozhuan.ttf"));
//      TypefaceCompat.createFromResourcesFontFile(context, context.resources,R.font.xiaozhuan, "", 0)
        seal_txt.setText(txt)
    }
}