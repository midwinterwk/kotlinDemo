package com.dimsum.behavioralpattern.mediator

import java.util.*

class ChatRoom {
    companion object {
        fun showMessage(user: User, message: String) {
            println(Date().toString() + " [${user.name}] : $message")
        }
    }
}