package com.dimsum.creationalpattern.abstractfactory

class Square : Shape {
    override fun draw() {
        System.out.println("Inside Square::draw() method.")
    }
}