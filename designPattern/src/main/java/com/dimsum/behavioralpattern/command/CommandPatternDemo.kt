package com.dimsum.behavioralpattern.command

class CommandPatternDemo {
    companion object {
        fun test() {
            val abcStock = Stock()

            val buyStockOrder = BuyStock(abcStock)
            val sellStockOrder = SellStock(abcStock)

            val broker = Broker()
            broker.takeOrder(buyStockOrder)
            broker.takeOrder(sellStockOrder)

            broker.placeOrders()
        }
    }
}