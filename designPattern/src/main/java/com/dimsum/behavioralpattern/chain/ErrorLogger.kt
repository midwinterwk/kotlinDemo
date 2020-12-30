package com.dimsum.behavioralpattern.chain

class ErrorLogger(level: Int) : AbstractLogger() {
    init {
        this.level = level
    }

    override fun write(message: String?) {
        println("Error Console::Logger: $message")
    }
}