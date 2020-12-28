package com.dimsum.creationalpattern.factory

class ShapeFactory {
    fun getShape(shapeType: String): Shape? {
        if (shapeType == null) {
            return null
        }
        if (shapeType.equals("RECTANGLE")) {
            return Rectangle()
        } else if (shapeType.equals("SQUARE")) {
            return Square()
        } else if (shapeType.equals("CIRCLE")) {
            return Circle()
        }
        return null
    }
}