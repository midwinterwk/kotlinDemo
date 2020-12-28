package com.dimsum.behavioralpattern.template

abstract class Game {
    abstract fun initialize()
    abstract fun startPlay()
    abstract fun endPlay()

    //模板
    fun play() {
        initialize()
        startPlay()
        endPlay()
    }
}