package com.dimsum.writinglibrary.pen

data class ControllerPoint (var x: Float = 0F, var y: Float = 0F, var width: Float = 0F,
                            var alpha: Int = 255, var isPoint: Boolean = false, var color: Int = IPenConfig.PEN_CORLOUR)