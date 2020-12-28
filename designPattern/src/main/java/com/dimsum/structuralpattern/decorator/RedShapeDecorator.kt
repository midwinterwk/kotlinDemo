package com.dimsum.structuralpattern.decorator

class RedShapeDecorator(decoratedShape: Shape) : ShapeDecorator(decoratedShape) {
    override fun draw() {
        setRedBorder(decoratedShape)
        decoratedShape.draw()
    }

    private fun setRedBorder(decoratedShape: Shape) {
        println("Border Color: Red")
    }
}