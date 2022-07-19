package com.example.github.Search.Detail

data class DetailResponse(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val followers : Int,
    val following : Int,
    val company : String,
    val public_repos : Int,
    val location : String
)
