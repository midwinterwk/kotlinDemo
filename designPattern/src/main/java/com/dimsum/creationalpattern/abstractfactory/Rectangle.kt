package com.dimsum.creationalpattern.abstractfactory

class Rectangle : Shape {
    override fun draw() {
        System.out.println("Inside Rectangle::draw() method.")
    }
}