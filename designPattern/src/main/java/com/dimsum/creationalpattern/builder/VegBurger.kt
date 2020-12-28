package com.dimsum.creationalpattern.builder

class VegBurger : Burger() {
    override fun price(): Float {
        return 25.0f
    }

    override fun name(): String {
        return "Veg Burger"
    }
}