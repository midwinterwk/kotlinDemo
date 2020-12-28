package com.dimsum.structuralpattern.decorator

class DecoratorPatternDemo {
    companion object {
        fun test() {
            val circle = Circle()
            val redCircle = RedShapeDecorator(Circle())
            val redRectangle =  RedShapeDecorator(Rectangle())

            println("Circle with normal border")
            circle.draw()

            println("\nCircle of red border")
            redCircle.draw()

            println("\nRectangle of red border")
            redRectangle.draw()
        }
    }
}