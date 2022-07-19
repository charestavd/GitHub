package com.example.github.Search.Detail.Fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.API.APIConfig
import com.example.github.API.APIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<APIResponse>>()

    fun setListFollowers (username : String) {
        with(APIConfig) {
            with(APIIntance) {
                getFollowers(username)
                        .enqueue(object  : Callback<ArrayList<APIResponse>> {
                            override fun onResponse(
                                call: Call<ArrayList<APIResponse>>,
                                response: Response<ArrayList<APIResponse>>
                            ) {
                                if(response.isSuccessful){
                                    listFollowers.postValue(response.body())
                                }
                            }

                            override fun onFailure(call: Call<ArrayList<APIResponse>>, t: Throwable) {
                                t.message?.let { Log.d("onFailure", it) }
                            }

                        })
            }
        }
    }

    fun getListFollowers(): LiveData<ArrayList<APIResponse>> {
        return listFollowers
    }
}
