package com.dimsum.creationalpattern.abstractfactory

class Blue : Color {
    override fun fill() {
        System.out.println("Inside Blue::fill() method.")
    }
}