package com.kou.fisaa.data.remote

import com.kou.fisaa.data.entities.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface FisaaApi {

    /** Get User **/
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    /** Login **/
    @POST("users/login")
    suspend fun login(@Body loginQuery: LoginQuery): Response<LoginResponse>

    /** Sign Up **/
    @Multipart
    @POST("users/")
    suspend fun signUp(@PartMap() partMap: Map<String, RequestBody>): Response<User>


    /** ads **/
    @GET("advertisements")
    suspend fun getAds(): Response<AdsResponse>

    @POST("advertisements/flights")
    suspend fun searchFlights(@Body searchQuery: FlightSearchQuery): Response<TripsResponse>

    @GET("advertisements/flights")
    suspend fun getAllFLights(): Response<TripsResponse>

    @GET("advertisements/upcoming")
    suspend fun getUpcomingFlights(): Response<FlightsResponse>

    @GET("advertisements/top")
    suspend fun getTopFlights(): Response<FlightsResponse>

    @POST("advertisements/search/dates")
    suspend fun searchFlights(@Body searchDatesQuery: FlightSearchDatesQuery): Response<TripsResponse>

    @POST("advertisements/")
    suspend fun postAd(@Body advertisement: AdsQuery): Response<AdsQuery>

    //@Headers("Content-Type: multipart/form-data")
    @Multipart
    @POST("parcels/")
    suspend fun postParcel(
        @PartMap() partMap: Map<String, RequestBody>
    ): Response<Parcel>
}