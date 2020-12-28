package com.dimsum.creationalpattern.builder

class Meal {
    private val items: ArrayList<Item> = ArrayList()

    fun addItem(item: Item) {
        items.add(item)
    }

    fun getCost() : Float {
        var cost = 0.0f
        for (item in items) {
            cost += item.price()
        }
        return cost
    }

    fun showItems() {
        for (item in items) {
            println("Item :" + item.name())
            println(", Packing :" + item.packing().pack())
            println(", Price :" + item.price())
        }
    }
}