package com.dimsum.behavioralpattern.strategy

class StrategyPatternDemo {
    companion object {
        fun test() {
            Context(OperationAdd()).apply {
                println("10 + 5 = ${executeStrategy(10, 5)}")
            }

            Context(OperationSubtract()).apply {
                println("10 - 5 = ${executeStrategy(10, 5)}")
            }

            Context(OperationMultiply()).apply {
                println("10 * 5 = ${executeStrategy(10, 5)}")
            }
        }
    }
}