package com.example.github.Search.Favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission3.model.FavoriteDao
import com.example.submission3.model.FavoriteUser

@Database( entities = [FavoriteUser::class], version = 1 )

abstract class UserFavorite : RoomDatabase(){
    companion object{
        var INSTANCE : UserFavorite? = null


        fun getDatabase (context: Context):UserFavorite? {
            if(INSTANCE == null){
                synchronized(UserFavorite::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserFavorite::class.java, "database list").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteDao(): FavoriteDao
}