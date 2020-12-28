package com.dimsum.behavioralpattern.state

class StatePatternDemo {
    companion object {
        fun test() {
            val context = Context()

            val startState = StartState()
            startState.doAction(context)
            println(context.state.toString())

            StopState().apply {
                doAction(context)
            }
            println(context.state.toString())
        }
    }
}