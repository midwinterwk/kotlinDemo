package com.dimsum.behavioralpattern.visitor

class VisitorPatternDemo {
    companion object {
        fun test() {
            Computer().accept(ComputerPartDisplayVisitor())
        }
    }
}