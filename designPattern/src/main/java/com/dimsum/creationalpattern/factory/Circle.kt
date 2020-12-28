package com.dimsum.creationalpattern.factory

class Circle : Shape {
    override fun draw() {
        System.out.println("Inside Circle::draw() method.")
    }
}