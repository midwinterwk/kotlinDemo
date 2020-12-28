package com.dimsum.structuralpattern.bridge

class RedCircle : DrawAPI {
    override fun drawCircle(radius: Int, x: Int, y: Int) {
        println("Drawing Circle[ color: red, radius: $radius, x: $x, y: $y]")
    }
}