package com.example.submission3.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite (favoriteUser: FavoriteUser)

    @Query("SELECT*FROM favorite_user")
    fun getFavorite():LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id= :id")
    suspend fun checkUser(id : Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id ")
    suspend fun removeFavorite(id:Int): Int

}