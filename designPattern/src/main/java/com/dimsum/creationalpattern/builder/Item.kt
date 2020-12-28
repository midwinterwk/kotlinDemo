package com.dimsum.creationalpattern.builder

interface Item {
    fun name(): String
    fun packing(): Packing
    fun price(): Float
}