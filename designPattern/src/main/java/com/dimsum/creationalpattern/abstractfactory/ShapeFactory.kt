package com.dimsum.creationalpattern.abstractfactory

class ShapeFactory : AbstractFactory() {

    override fun getShape(shapeType: String): Shape? {
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

    override fun getColor(color: String): Color? {
        return null
    }
}