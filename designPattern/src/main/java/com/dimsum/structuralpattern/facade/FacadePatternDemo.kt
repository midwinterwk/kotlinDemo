package com.dimsum.structuralpattern.facade

class FacadePatternDemo {
    companion object {
        fun test() {
            ShapeMaker().apply {
                drawCircle()
                drawRectangle()
                drawSquare()
            }
        }
    }
}