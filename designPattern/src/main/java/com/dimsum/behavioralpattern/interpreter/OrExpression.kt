package com.dimsum.behavioralpattern.interpreter

class OrExpression(val expr1:Expression, val expr2: Expression): Expression {
    override fun interpret(context: String): Boolean {
        return expr1.interpret(context) || expr2.interpret(context)
    }
}