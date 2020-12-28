package com.dimsum.behavioralpattern.chain

abstract class AbstractLogger {

    companion object {
        var INFO = 1
        var DEBUG = 2
        var ERROR = 3
    }

    protected var level = 0

    //责任链中的下一个元素
    protected var nextLogger: AbstractLogger? = null

    fun setNextLogger(nextLogger: AbstractLogger): AbstractLogger {
        this.nextLogger = nextLogger
        return this
    }

    fun logMessage(level: Int, message: String?) {
        if (this.level <= level) {
            write(message)
        }
        if (nextLogger != null) {
            nextLogger!!.logMessage(level, message)
        }
    }

    protected abstract fun write(message: String?)
}