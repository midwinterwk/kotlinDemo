package com.dimsum.creationalpattern.abstractfactory

class AbstractFactoryPatternDemo {

    companion object {
        fun test() {
            FactoryProducer.apply {
                this.getFactory("SHAPE").let {
                    it?.getShape("SQUARE")?.draw()
                    it?.getShape("RECTANGLE")?.draw()
                    it?.getShape("CIRCLE")?.draw()
                }
                this.getFactory("COLOR").let {
                    it?.getColor("RED")?.fill()
                    it?.getColor("GREEN")?.fill()
                    it?.getColor("BLUE")?.fill()
                }
            }

        }
    }
}