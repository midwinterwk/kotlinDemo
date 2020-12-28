package com.dimsum.behavioralpattern.memento

class CareTaker {
    private val mementoList: MutableList<Memento> = ArrayList()

    fun add(state: Memento) {
        mementoList.add(state)
    }

    fun get(index: Int): Memento = mementoList.get(index)

}