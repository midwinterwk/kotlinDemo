package com.dimsum.structuralpattern.proxy

class ProxyImage(private val fileName: String): Image {
    var realImage: RealImage? = null
    override fun display() {
        if (realImage == null) {
            realImage = RealImage(fileName)
        }
        realImage?.display()
    }
}