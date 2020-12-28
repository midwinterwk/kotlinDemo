package com.dimsum.behavioralpattern.nullobject

class NullCustomer: AbstractCustomer() {

    override var name: String?
        get() = "Not Available in Customer Database"
        set(value) {name = value}

    override fun isNil(): Boolean {
        return true
    }
}