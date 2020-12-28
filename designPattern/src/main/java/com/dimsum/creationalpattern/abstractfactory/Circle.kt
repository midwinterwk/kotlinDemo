package com.dimsum.creationalpattern.abstractfactory

class Circle : Shape {
    override fun draw() {
        System.out.println("Inside Circle::draw() method.")
    }
}