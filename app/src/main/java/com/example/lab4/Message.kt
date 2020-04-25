package com.example.lab4


data class Message(
    val chatId: String,
    val message: String,
    val sender: String,
    val timestamp: String
){
constructor(): this("","","","")
}