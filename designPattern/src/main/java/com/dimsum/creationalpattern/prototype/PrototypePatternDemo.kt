package com.dimsum.creationalpattern.prototype

class PrototypePatternDemo {

    companion object {
        fun test() {
            ShapeCache.loadCache()

            val shape = ShapeCache.getShape("1")
            println("Shape : " + shape?.type)

            val shape2 = ShapeCache.getShape("2")
            println("Shape : " + shape2?.type)

            ShapeCache.getShape("3")?.let {
                println("Shape : " + it.type)
            }
        }
    }
}