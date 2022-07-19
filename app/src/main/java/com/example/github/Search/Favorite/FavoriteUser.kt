package com.example.submission3.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login : String,
    @PrimaryKey
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String

):Serializable
