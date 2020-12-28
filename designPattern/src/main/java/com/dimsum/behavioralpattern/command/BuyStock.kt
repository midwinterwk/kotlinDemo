package com.dimsum.behavioralpattern.command

class BuyStock(private val abcStock:Stock): Order {
    override fun execute() {
        abcStock.buy()
    }

}