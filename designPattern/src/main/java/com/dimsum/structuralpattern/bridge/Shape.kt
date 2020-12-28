package com.dimsum.structuralpattern.bridge

abstract class Shape protected constructor(protected var drawAPI: DrawAPI) {
    abstract fun draw()
}