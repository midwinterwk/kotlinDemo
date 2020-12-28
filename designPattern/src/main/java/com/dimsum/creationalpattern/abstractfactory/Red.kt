package com.dimsum.creationalpattern.abstractfactory

class Red : Color {
    override fun fill() {
        System.out.println("Inside Red::fill() method.")
    }
}