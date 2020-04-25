package com.example.lab4

class UserDto {
    var username: String = ""
    var email: String = ""

    constructor(){}

    constructor(username:String, email:String){
        this.username = username
        this.email = email
    }

}