package com.dimsum.behavioralpattern.interpreter

class InterpreterPatternDemo {
    companion object {
        fun test() {
            val isMale: Expression = getMaleExpression()
            val isMarriedWoman: Expression = getMarriedWomanExpression()

            println("John is male? ${isMale.interpret("John")}")
            println("Julie is a married women? ${isMarriedWoman.interpret("Married Julie")}")
        }

        private fun getMaleExpression(): Expression {
            val robert: Expression = TerminalExpression("Robert")
            val john: Expression = TerminalExpression("John")
            return OrExpression(robert, john)
        }

        private fun getMarriedWomanExpression(): Expression {
            val julie: Expression = TerminalExpression("Julie")
            val married: Expression = TerminalExpression("Married")
            return AndExpression(julie, married)
        }
    }
}