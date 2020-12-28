package com.dimsum.behavioralpattern.mediator

class User(val name:String) {
    fun sendMessage(message: String) {
        ChatRoom.showMessage(this, message)
    }
}