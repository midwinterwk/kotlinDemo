package com.dimsum.behavioralpattern.chain

class ErrorLogger(var leval:Int): AbstractLogger() {
    override fun write(message: String?) {
        println("Error Console::Logger: $message")
    }
}