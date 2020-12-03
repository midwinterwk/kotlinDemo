package com.dimsum.writinglibrary.pen

import android.graphics.Color

/**
 * @author shiming
 * @version v1.0 create at 2017/10/10
 * @des  笔的设置，但是有些笔的设置最好不要放在这里，不要笔的颜色和宽度
 */
interface IPenConfig {
    companion object {
        /**
         * 清除画布
         */
        const val STROKE_TYPE_ERASER = 0

        /**
         * 撤销
         */
        const val STROKE_TYPE_UNDO = -1

        /**
         * 钢笔
         */
        const val STROKE_TYPE_PEN = 1 // 钢笔

        /**
         * 毛笔
         */
        const val STROKE_TYPE_BRUSH = 2 // 毛笔

        //设置笔的宽度
        const val PEN_WIDTH = 100

        var WIDTH_RATIO = 0.5F
        fun getPenWidth() :Float {
            return PEN_WIDTH * WIDTH_RATIO
        }
        fun getPenWidth(progress :Int) :Int {
            WIDTH_RATIO = 0.2F + progress/100F
            return (PEN_WIDTH * WIDTH_RATIO).toInt()
        }

        //笔的颜色
        var PEN_CORLOUR = Color.parseColor("#111111")

        //这个控制笔锋的控制值
        const val DIS_VEL_CAL_FACTOR = 0.02f

        //手指在移动的控制笔的变化率  这个值越大，线条的粗细越加明显
        //float WIDTH_THRES_MAX = 0.6f;
        const val WIDTH_THRES_MAX = 20f

        //绘制计算的次数，数值越小计算的次数越多，需要折中
        const val STEPFACTOR = 10
    }
}