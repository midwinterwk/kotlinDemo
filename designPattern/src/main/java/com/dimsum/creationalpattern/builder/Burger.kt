package com.dimsum.creationalpattern.builder

abstract class Burger : Item{
    override fun packing(): Packing {
        return Wrapper()
    }

    override abstract fun price(): Float
}