package com.dimsum.behavioralpattern.visitor

class Mouse : ComputerPart {
    override fun accept(computerPartVisitor: ComputerPartVisitor) {
        computerPartVisitor.visit(this)
    }
}