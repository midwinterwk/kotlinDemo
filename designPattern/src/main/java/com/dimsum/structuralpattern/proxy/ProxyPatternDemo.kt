package com.dimsum.structuralpattern.proxy

class ProxyPatternDemo {

    companion object {
        fun test() {
            val image = ProxyImage("test_10mb.jpg")
            image.display()
            println()
            image.display()
        }
    }
}