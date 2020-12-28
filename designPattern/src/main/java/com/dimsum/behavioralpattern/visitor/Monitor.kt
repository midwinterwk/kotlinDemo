package com.dimsum.behavioralpattern.visitor

class Monitor: ComputerPart {
    override fun accept(computerPartVisitor: ComputerPartVisitor) {
        computerPartVisitor.visit(this)
    }
}