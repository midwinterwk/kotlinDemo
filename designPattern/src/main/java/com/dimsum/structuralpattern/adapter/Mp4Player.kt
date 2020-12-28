package com.dimsum.structuralpattern.adapter

class Mp4Player : AdvancedMediaPlayer {
    override fun playVlc(fileName: String) {
        // Do Nothing
    }

    override fun playMp4(fileName: String) {
        println("Playing mp4 file. Name: $fileName")
    }
}