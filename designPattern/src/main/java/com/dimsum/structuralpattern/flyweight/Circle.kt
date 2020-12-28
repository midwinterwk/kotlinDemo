package com.dimsum.structuralpattern.flyweight

import android.R.attr


class Circle(val color: String) : Shape {
    private var x: Int? = null
    private var y: Int? = null
    private var radius: Int? = null

    fun setX(x: Int) {
        this.x = x
    }

    fun setY(y: Int) {
        this.y = y
    }

    fun setRadius(radius: Int) {
        this.radius = radius
    }

    override fun draw() {
        println(
            "Circle: Draw() [Color : $color, x : $x, y : $y, radius : $radius"
        )
    }
}