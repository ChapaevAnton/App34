package com.w4eret1ckrtb1tch.app34.data.source

import com.w4eret1ckrtb1tch.app34.data.dto.user.UserDto
import com.w4eret1ckrtb1tch.app34.data.dto.user.UsersDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("api/users?page=1")
    fun getUsers(): Call<UsersDto>

    @GET("api/users")
    fun getUsers(@Query("page") page: Int): Call<UsersDto>

    @GET("/api/users/{id}")
    fun getUser(@Path("id") id: Int): Call<UserDto>

}