package com.dimsum.creationalpattern.abstractfactory

class ColorFactory : AbstractFactory() {
    override fun getColor(color: String): Color? {
        if (color == null) {
            return null
        }
        if (color.equals("RED")) {
            return Red()
        } else if (color.equals("GREEN")) {
            return Green()
        } else if (color.equals("BLUE")) {
            return Blue()
        }

        return null
    }

    override fun getShape(shape: String): Shape? {
        return null
    }
}