package com.dimsum.creationalpattern.prototype

abstract class Shape : Cloneable {
    var id: String? = null
    var type: String? = null
        protected set

    abstract fun draw()

    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }
}