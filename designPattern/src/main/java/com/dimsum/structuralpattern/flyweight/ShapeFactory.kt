package com.dimsum.structuralpattern.flyweight

class ShapeFactory {
    //包裹你要调取的方法
    companion object {
        private val circleMap: HashMap<String, Shape> = HashMap()

        fun getCircle(color: String): Shape? {
            var circle = circleMap[color] as Circle?
            if (circle == null) {
                circle = Circle(color)
                circleMap[color] = circle
                println("Creating circle of color : $color")
            }
            return circle
        }
    }
}