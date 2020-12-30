package com.dimsum.behavioralpattern.chain


class FileLogger(level: Int) : AbstractLogger() {
    init {
        this.level = level
    }

    override fun write(message: String?) {
        println("File::Logger: $message")
    }
}