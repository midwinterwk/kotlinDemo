package com.dimsum.structuralpattern.flyweight

class FlyweightPatternDemo {

    companion object {
        private val colors = arrayOf("Red", "Green", "Blue", "White", "Black")

        fun test() {
            for (index in 0..20) {
                (ShapeFactory.getCircle(getRandomColor()) as Circle)?.apply {
                    setX(getRandomX())
                    setY(getRandomY())
                    setRadius(100)
                    draw()
                }
            }
        }

        private fun getRandomColor(): String {
            return colors[(Math.random() * colors.size).toInt()]
        }

        private fun getRandomX(): Int {
            return (Math.random() * 100).toInt()
        }

        private fun getRandomY(): Int {
            return (Math.random() * 100).toInt()
        }
    }
}