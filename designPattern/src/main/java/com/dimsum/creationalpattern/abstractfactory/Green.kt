package com.dimsum.creationalpattern.abstractfactory

class Green : Color {
    override fun fill() {
        System.out.println("Inside Green::fill() method.")
    }
}