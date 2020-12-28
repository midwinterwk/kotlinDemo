package com.dimsum.behavioralpattern.nullobject

class CustomerFactory {
    companion object {
        val names = arrayOf("Rob", "Joe", "Julie")

        fun getCustomer(name: String?): AbstractCustomer? {
            for (i in 0 until names.size) {
                if (names[i].equals(name, true)) {
                    return RealCustomer(name!!)
                }
            }
            return NullCustomer()
        }
    }
}