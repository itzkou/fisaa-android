package com.kou.fisaa.data.remote

import com.kou.fisaa.data.entities.FlightSearchQuery
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.SignUpQuery
import javax.inject.Inject

class FisaaRemote @Inject constructor(
    private val fisaaApi: FisaaApi
) : RetrofitSource() {

    suspend fun login(loginQuery: LoginQuery) =
        getResource { fisaaApi.login(loginQuery) }

    suspend fun getUser(id: Int) =
        getResource { fisaaApi.getUser(id) }

    suspend fun signUp(signUpQuery: SignUpQuery) =
        getResource { fisaaApi.signUp(signUpQuery) }

    suspend fun searchFlights(searchQuery: FlightSearchQuery) =
        getResource { fisaaApi.searchFlights(searchQuery) }

    suspend fun getUpcomingFlights() =
        getResource { fisaaApi.getUpcomingFlights() }

    suspend fun getTopFlights() =
        getResource { fisaaApi.getTopFlights() }

    suspend fun getALlFlights() =
        getResource { fisaaApi.getAllFLights() }

    suspend fun getAds() =
        getResource { fisaaApi.getAds() }


}