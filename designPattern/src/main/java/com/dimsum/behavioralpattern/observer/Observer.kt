package com.dimsum.behavioralpattern.observer

abstract class Observer {
    protected lateinit var subject: Subject
    abstract fun update()
}