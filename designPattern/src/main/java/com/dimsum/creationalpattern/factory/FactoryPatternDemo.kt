package com.dimsum.creationalpattern.factory

class FactoryPatternDemo {

    companion object {
        fun test() {
            ShapeFactory().let {
                it.getShape("RECTANGLE")?.draw()
                it.getShape("SQUARE")?.draw()
                it.getShape("CIRCLE")?.draw()
            }

        }
    }
}