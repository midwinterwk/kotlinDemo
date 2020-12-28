package com.dimsum.behavioralpattern.template

class Cricket: Game() {
    override fun initialize() {
        println("Cricket Game Initialized! Start playing.")
    }

    override fun startPlay() {
        println("Cricket Game Started. Enjoy the game!")
    }

    override fun endPlay() {
        println("Cricket Game Finished!")
    }
}