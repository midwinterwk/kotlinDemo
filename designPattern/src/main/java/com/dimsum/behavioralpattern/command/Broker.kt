package com.dimsum.behavioralpattern.command

class Broker {
    private val orderList: MutableList<Order> = ArrayList()

    fun takeOrder(order: Order) {
        orderList.add(order)
    }

    fun placeOrders() {
        for (order in orderList) {
            order.execute()
        }
        orderList.clear()
    }
}