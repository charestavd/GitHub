package com.example.submission1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Avatars(
    var username : String,
    var name : String,
    var followers : String,
    var following : String,
    var location : String,
    val company : String,
    var repository : String,
    var photo : Int
) : Parcelable
