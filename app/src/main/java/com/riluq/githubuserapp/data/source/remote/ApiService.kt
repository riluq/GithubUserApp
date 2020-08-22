package com.riluq.githubuserapp.data.source.remote

import com.riluq.githubuserapp.data.source.remote.response.DetailUserResponse
import com.riluq.githubuserapp.data.source.remote.response.SearchUserResponse
import com.riluq.githubuserapp.data.source.remote.response.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun search(@Query("q") username: String?): Response<SearchUserResponse>

    @GET("users/{username}")
    suspend fun detailUser(@Path("username") username: String?): Response<DetailUserResponse>

    @GET("users/{username}/followers")
    suspend fun listFollower(@Path("username") username: String?): Response<List<User>>

    @GET("users/{username}/following")
    suspend fun listFollowing(@Path("username") username: String?): Response<List<User>>

}