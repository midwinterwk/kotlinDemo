package com.dimsum.creationalpattern.prototype

import java.util.*

object ShapeCache {

    private val shapeMap: Hashtable<String, Shape> = Hashtable<String, Shape>()

    fun getShape(shapeId: String?): Shape? {
        val cachedShape: Shape? = shapeMap.get(shapeId)
        return cachedShape?.clone() as Shape
    }

    // 对每种形状都运行数据库查询，并创建该形状
    // shapeMap.put(shapeKey, shape);
    // 例如，我们要添加三种形状
    fun loadCache() {
        val circle = Circle()
        circle.id = "1"
        shapeMap.put(circle.id, circle)
        val square = Square()
        square.id = "2"
        shapeMap.put(square.id, square)
        val rectangle = Rectangle()
        rectangle.id = "3"
        shapeMap.put(rectangle.id, rectangle)
    }
}