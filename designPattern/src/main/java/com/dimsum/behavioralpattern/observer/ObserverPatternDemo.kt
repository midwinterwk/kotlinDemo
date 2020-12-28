package com.dimsum.behavioralpattern.observer

class ObserverPatternDemo {
    companion object {
        fun test() {
            val subject = Subject()

            HexaObserver(subject)
            OctalObserver(subject)
            BinaryObserver(subject)

            println("First state change: 15")
            subject.state = 15
            println("Second state change: 10")
            subject.state = 10
        }
    }
}