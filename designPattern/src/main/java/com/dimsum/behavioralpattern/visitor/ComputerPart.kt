package com.dimsum.behavioralpattern.visitor

interface ComputerPart {
    fun accept(computerPartVisitor: ComputerPartVisitor)
}