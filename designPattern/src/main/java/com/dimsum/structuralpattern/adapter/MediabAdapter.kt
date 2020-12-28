package com.dimsum.structuralpattern.adapter

class MediaAdapter(audioType: String) : MediaPlayer {
    private lateinit var advancedMusicPlayer : AdvancedMediaPlayer

    init {
        if (audioType.equals("vlc", ignoreCase = true)) {
            advancedMusicPlayer = VlcPlayer()
        } else if (audioType.equals("mp4", ignoreCase = true)) {
            advancedMusicPlayer = Mp4Player()
        }
    }

    override fun play(audioType: String, fileName: String) {
        if(audioType == "vlc") {
            advancedMusicPlayer.playVlc(fileName)
        } else if(audioType == "mp4") {
            advancedMusicPlayer.playMp4(fileName)
        }
    }
}