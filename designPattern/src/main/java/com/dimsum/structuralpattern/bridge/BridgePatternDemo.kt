package com.dimsum.structuralpattern.bridge

class BridgePatternDemo {
    companion object {
        fun test() {
            Circle(100, 100, 10, RedCircle()).draw()
            Circle(100, 100, 10, GreenCircle()).draw()
        }
    }
}