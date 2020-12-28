package com.dimsum.behavioralpattern.interpreter

interface Expression {
    fun interpret(context: String) :Boolean
}