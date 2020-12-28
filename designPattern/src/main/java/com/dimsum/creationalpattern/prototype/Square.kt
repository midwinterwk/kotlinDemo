package com.dimsum.creationalpattern.prototype

class Square : Shape() {
    init {
        type = "Square"
    }
    override fun draw() {
        println("Inside Square::draw() method.")
    }
}