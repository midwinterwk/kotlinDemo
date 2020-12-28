package com.dimsum.behavioralpattern.mediator

class MediatorPatternDemo {
    companion object {
        fun test() {
            User("Robert").sendMessage("Hi! John!")
            User("John").sendMessage("Hello! Robert!")
        }
    }
}