package com.dimsum.creationalpattern.abstractfactory

class FactoryProducer {

    companion object {
        fun getFactory(choice: String): AbstractFactory? {
            if (choice.equals("SHAPE")) {
                return ShapeFactory()
            } else if(choice.equals("COLOR")) {
                return ColorFactory()
            }
            return null
        }
    }
}