package com.example.lab4

class Chat {
    var id:String = ""
    var email: String = ""
    var username: String = ""
    var LastMessage: String = ""
    var LastMessageTimestamp: String = ""

    constructor(){}

    constructor( id:String, email: String, username:String, LastMessage:String, timestamp:String){
        this.id = id
        this.email = email
        this.username = username
        this.LastMessage = LastMessage
        this.LastMessageTimestamp = timestamp
    }

}