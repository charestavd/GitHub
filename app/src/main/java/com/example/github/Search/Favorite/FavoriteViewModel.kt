package com.example.github.Search.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission3.model.FavoriteDao
import com.example.submission3.model.FavoriteUser

class FavoriteViewModel (application: Application): AndroidViewModel(application) {
    private var userDao : FavoriteDao?
    private var userDB : UserFavorite?

    init{
        userDB = UserFavorite.getDatabase(application)
        userDao = userDB?.favoriteDao()

    }

    fun getFavorite(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavorite()
    }
}