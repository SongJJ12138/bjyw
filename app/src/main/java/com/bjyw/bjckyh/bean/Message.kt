package com.bjyw.bjckyh.bean

class Message private constructor(val message: Map<String,Any>) {

    companion object {
        fun getInstance(message: Map<String,Any>): Message {
            return Message(message)
        }
    }

}