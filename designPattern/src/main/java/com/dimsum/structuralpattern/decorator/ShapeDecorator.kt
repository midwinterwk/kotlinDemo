package com.dimsum.structuralpattern.decorator

abstract class ShapeDecorator(protected val decoratedShape: Shape): Shape {
    override fun draw() {
        decoratedShape.draw()
    }
}