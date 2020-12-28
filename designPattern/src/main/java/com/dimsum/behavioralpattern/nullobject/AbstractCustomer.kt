package com.dimsum.behavioralpattern.nullobject

abstract class AbstractCustomer {
    open var name: String? = null
    abstract fun isNil(): Boolean
}