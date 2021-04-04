package com.kou.fisaa.data.remote

import com.kou.fisaa.data.entities.*
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
    suspend fun login(@Body loginQuery: LoginQuery): Response<LoginResponse>

    /** Sign Up **/
    @POST("users/")
    suspend fun signUp(@Body signUpQuery: SignUpQuery): Response<User>


    /** Flights **/
    @POST("advertisements/flights")
    suspend fun searchFlights(@Body searchQuery: FlightSearchQuery): Response<FlightSearchResponse>
}