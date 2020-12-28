package com.dimsum.behavioralpattern.chain

class ConsoleLogger(var leval:Int): AbstractLogger() {
    override fun write(message: String?) {
        println("Standard Console::Logger: $message")
    }
}