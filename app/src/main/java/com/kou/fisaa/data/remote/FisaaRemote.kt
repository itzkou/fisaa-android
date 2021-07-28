package com.kou.fisaa.data.remote

import com.kou.fisaa.data.entities.*
import okhttp3.RequestBody
import javax.inject.Inject

class FisaaRemote @Inject constructor(
    private val fisaaApi: FisaaApi
) : RetrofitSource() {

    suspend fun login(loginQuery: LoginQuery) =
        getResource { fisaaApi.login(loginQuery) }

    suspend fun getUser(id: String) =
        getResource { fisaaApi.getUser(id) }

    suspend fun signUp(signUpQuery: Map<String, RequestBody>) =
        getResource { fisaaApi.signUp(signUpQuery) }

    suspend fun searchFlights(searchQuery: FlightSearchQuery) =
        getResource { fisaaApi.searchFlights(searchQuery) }

    suspend fun getUpcomingFlights() =
        getResource { fisaaApi.getUpcomingFlights() }

    suspend fun getTopFlights() =
        getResource { fisaaApi.getTopFlights() }

    suspend fun getALlFlights() =
        getResource { fisaaApi.getAllFLights() }

    suspend fun getAd(id: String) =
        getResource { fisaaApi.getAd(id) }


    suspend fun getAds() =
        getResource { fisaaApi.getAds() }

    suspend fun getMyAds(id: String) =
        getResource { fisaaApi.getMyAds(id) }


    suspend fun searchFlights(searchDatesQuery: FlightSearchDatesQuery) =
        getResource { fisaaApi.searchFlights(searchDatesQuery) }

    suspend fun postAd(advertisement: AdsQuery) =
        getResource { fisaaApi.postAd(advertisement) }

    suspend fun postParcel(partMap: Map<String, RequestBody>) =
        getResource { fisaaApi.postParcel(partMap) }

    suspend fun searchAds(searchQuery: AdSearchQuery) =
        getResource { fisaaApi.searchAds(searchQuery) }

    suspend fun updateParcel(parcelQuery: ParcelQuery, id: String) =
        getResource { fisaaApi.updateParcel(parcelQuery, id) }


}