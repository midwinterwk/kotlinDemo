package com.dimsum.structuralpattern.proxy

class RealImage(private val fileName: String): Image {
    init {
        loadFromDisk(fileName)
    }

    private fun loadFromDisk(fileName: String) {
        println("Loading $fileName")
    }

    override fun display() {
        println("Displaying $fileName")
    }
}