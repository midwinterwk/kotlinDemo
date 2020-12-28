package com.dimsum.creationalpattern.builder

class Pepsi : ColdDrink() {
    override fun price(): Float {
        return 35.5f
    }

    override fun name(): String {
        return "Pepsi"
    }
}