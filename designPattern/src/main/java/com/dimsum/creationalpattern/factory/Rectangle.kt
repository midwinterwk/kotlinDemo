package com.dimsum.creationalpattern.factory

class Rectangle : Shape {
    override fun draw() {
        System.out.println("Inside Rectangle::draw() method.")
    }
}