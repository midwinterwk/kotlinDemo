package com.dimsum.behavioralpattern.observer

class OctalObserver(subject: Subject?) : Observer() {

    init {
        this.subject = subject!!
        this.subject.attach(this)
    }

    override fun update() {
        println(
            "Octal String: "
                    + Integer.toOctalString(subject.state)
        )
    }
}