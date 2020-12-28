package com.dimsum.behavioralpattern.observer

class HexaObserver(subject: Subject?): Observer() {
    init {
        this.subject = subject!!
        this.subject.attach(this)
    }

    override fun update() {
        System.out.println( "Hex String: "
                + Integer.toHexString( subject.state ).toUpperCase() );
    }
}