package com.dimsum.behavioralpattern.nullobject

class NullPatternDemo {
    companion object {
        fun test() {
            val customer1 = CustomerFactory.getCustomer("Rob")!!
            val customer2 = CustomerFactory.getCustomer("Bob")!!
            val customer3 = CustomerFactory.getCustomer("Julie")!!
            val customer4 = CustomerFactory.getCustomer("Laura")!!

            println("Customers")
            println(customer1.name)
            println(customer2.name)
            println(customer3.name)
            println(customer4.name)
        }
    }
}