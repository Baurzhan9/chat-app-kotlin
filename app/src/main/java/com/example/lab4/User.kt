package com.example.lab4

data class User(
    val username: String,
    val email: String,
    val uid: String
)   {
    constructor(): this("","", "")
}