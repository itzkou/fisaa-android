package com.kou.fisaa.data.remote

import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FisaaApi {

    /** Get User **/
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    /** Login **/
    @POST("users/login")
    suspend fun login(@Body user: User): Response<LoginResponse>

    /** Sign Up **/
    suspend fun signUp(@Body user: User): Response<User>
}