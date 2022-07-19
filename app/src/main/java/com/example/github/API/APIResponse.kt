package com.example.github.API


data class APIResponse (
    val login : String,
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String
)

