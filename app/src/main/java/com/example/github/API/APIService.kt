package com.example.github.API

import com.example.github.Search.Detail.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getAPICall(@Query("q")query: String): Call<APIList>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(
        @Path("username") username : String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username : String
    ):Call<ArrayList<APIResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username : String
    ):Call<ArrayList<APIResponse>>

}