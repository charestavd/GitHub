package com.example.github.Search.Detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.github.API.APIConfig
import com.example.github.Search.Favorite.UserFavorite
import com.example.submission3.model.FavoriteDao
import com.example.submission3.model.FavoriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel  (application: Application): AndroidViewModel(application){
    val users = MutableLiveData<DetailResponse>()

    private var userDao : FavoriteDao?
    private var userDB : UserFavorite?

    init{
        userDB = UserFavorite.getDatabase(application)
        userDao = userDB?.favoriteDao()

    }
    fun setUsersDetail (username : String){
        APIConfig.APIIntance
            .getDetail(username)
            .enqueue(object : Callback <DetailResponse>{
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful){
                        users.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    t.message?.let { Log.d("onFailure", it) }
                }

            })
    }

    fun getUsersDetail():LiveData<DetailResponse>{
        return users
    }

    fun addFavorite(username: String, id : Int, avatar_url :String, followers_url : String, following_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser( username, id, avatar_url, followers_url, following_url)
            userDao?.addFavorite(user)
        }
    }

    suspend fun checkFavorite(id : Int) = userDao?.checkUser(id)

    fun removeFavorite (id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}