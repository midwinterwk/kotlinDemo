package com.dimsum.behavioralpattern.observer

class BinaryObserver(subject: Subject?): Observer() {
    init {
        this.subject = subject!!
        this.subject.attach(this)
    }

    override fun update() {
        println( "Binary String: "
                + Integer.toBinaryString( subject.state ) );
    }
}