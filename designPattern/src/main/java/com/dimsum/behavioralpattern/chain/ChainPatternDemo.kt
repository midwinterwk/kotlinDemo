package com.dimsum.behavioralpattern.chain

class ChainPatternDemo {
    companion object {
        fun test() {
            val loggerChain: AbstractLogger = getChainOfLoggers()

            loggerChain.logMessage(AbstractLogger.INFO,
                "This is an information.")

            loggerChain.logMessage(AbstractLogger.DEBUG,
                "This is a debug level information."
            )

            loggerChain.logMessage(AbstractLogger.ERROR,
                "This is an error information."
            )
        }

        fun getChainOfLoggers(): AbstractLogger {
            val errorLogger: AbstractLogger = ErrorLogger(AbstractLogger.ERROR)
            val fileLogger: AbstractLogger = FileLogger(AbstractLogger.DEBUG)
            val consoleLogger: AbstractLogger = ConsoleLogger(AbstractLogger.INFO)
            errorLogger.setNextLogger(fileLogger)
            fileLogger.setNextLogger(consoleLogger)
            return errorLogger
        }
    }
}