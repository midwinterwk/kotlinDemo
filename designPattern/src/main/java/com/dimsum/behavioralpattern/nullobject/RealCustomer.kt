package com.dimsum.behavioralpattern.nullobject

class RealCustomer(name: String) : AbstractCustomer() {
    init {
        super.name = name
    }

    override var name: String?
        get() = super.name
        set(value) {name = value}


    override fun isNil(): Boolean {
        return false
    }
}