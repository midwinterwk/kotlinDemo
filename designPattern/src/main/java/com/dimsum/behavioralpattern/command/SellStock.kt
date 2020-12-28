package com.dimsum.behavioralpattern.command

class SellStock(private val abcStock:Stock): Order {
    override fun execute() {
        abcStock.sell()
    }
}