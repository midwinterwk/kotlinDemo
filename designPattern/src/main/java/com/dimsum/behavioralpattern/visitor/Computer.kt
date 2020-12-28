package com.dimsum.behavioralpattern.visitor

class Computer : ComputerPart {
    var parts: Array<ComputerPart> = arrayOf(Mouse(), Keyboard(), Monitor())

    override fun accept(computerPartVisitor: ComputerPartVisitor) {
        for (i in 0 until parts.size) {
            parts[i].accept(computerPartVisitor)
        }
        computerPartVisitor.visit(this)
    }
}