package com.example.github.Search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.API.APIConfig
import com.example.github.API.APIList
import com.example.github.API.APIResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel(){

    val searchListUser = MutableLiveData<ArrayList<APIResponse>>()

    fun setSearchUsers(query : String) {
        APIConfig.APIIntance
            .getAPICall(query)
            .enqueue(object : Callback<APIList> {
                override fun onResponse(
                    call: Call<APIList>,
                    response: Response<APIList>
                ) {
                    if (response.isSuccessful) {
                        searchListUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<APIList>, t: Throwable) {

                    t.message?.let { Log.d( "onFailure:", it) }
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<APIResponse>> {
        return searchListUser
    }
}