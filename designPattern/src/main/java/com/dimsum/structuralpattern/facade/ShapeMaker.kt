package com.dimsum.structuralpattern.facade

class ShapeMaker {
    private val circle: Circle
    private val rectangle: Rectangle
    private val square: Square

    init {
        circle = Circle()
        rectangle = Rectangle()
        square = Square()
    }

    fun drawCircle() {
        circle.draw()
    }

    fun drawRectangle() {
        rectangle.draw()
    }

    fun drawSquare() {
        square.draw()
    }
}