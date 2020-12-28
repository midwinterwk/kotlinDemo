package com.dimsum.structuralpattern.adapter

class AdapterPatternDemo {
    companion object {
        fun test() {
            AudioPlayer().apply {
                play("mp3", "beyond the horizon.mp3")
                play("mp4", "alone.mp4")
                play("vlc", "far far away.vlc")
                play("avi", "mind me.avi")
            }
        }
    }
}