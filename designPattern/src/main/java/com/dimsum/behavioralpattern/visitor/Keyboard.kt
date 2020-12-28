package com.dimsum.behavioralpattern.visitor

class Keyboard: ComputerPart {
    override fun accept(computerPartVisitor: ComputerPartVisitor) {
        computerPartVisitor.visit(this)
    }
}