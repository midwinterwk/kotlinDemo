package com.dimsum.behavioralpattern.command

class Stock {
    private val name = "ABC"
    private val quantity = 10
    fun buy() {
        println("Stock [ Name: $name, Quantity: $quantity ] bought")
    }
    fun sell() {
        println("Stock [ Name: $name, Quantity: $quantity ] sold")
    }
}