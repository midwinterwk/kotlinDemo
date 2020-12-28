package com.dimsum.behavioralpattern.chain


class FileLogger(var leval:Int): AbstractLogger() {
    override fun write(message: String?) {
        println("File::Logger: $message")
    }
}