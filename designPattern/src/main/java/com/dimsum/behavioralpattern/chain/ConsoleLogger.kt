package com.dimsum.behavioralpattern.chain

class ConsoleLogger(level: Int) : AbstractLogger() {
    init {
        this.level = level
    }

    override fun write(message: String?) {
        println("Standard Console::Logger: $message")
    }
}