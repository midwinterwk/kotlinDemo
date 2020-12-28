package com.dimsum.creationalpattern.builder

abstract class ColdDrink : Item {
    override fun packing(): Packing {
        return Bottle()
    }

    override abstract fun price(): Float
}