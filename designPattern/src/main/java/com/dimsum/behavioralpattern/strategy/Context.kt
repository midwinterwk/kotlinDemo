package com.dimsum.behavioralpattern.strategy

class Context(var strategy: Strategy) {
    fun executeStrategy(num1: Int, num2: Int): Int{
        return strategy.doOperation(num1, num2)
    }
}