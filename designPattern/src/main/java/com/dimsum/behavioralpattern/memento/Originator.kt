package com.dimsum.behavioralpattern.memento

class Originator {
    internal var state: String? = null

    fun saveStateToMemento(): Memento? {
        return state?.let { Memento(it) }
    }

    fun getStateFromMemento(Memento: Memento) {
        state = Memento.state
    }
}