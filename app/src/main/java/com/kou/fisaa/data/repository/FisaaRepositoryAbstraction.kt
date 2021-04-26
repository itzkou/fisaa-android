package com.kou.fisaa.data.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.*
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FisaaRepositoryAbstraction {
    /*** Remote ***/
    suspend fun login(loginQuery: LoginQuery): Flow<Resource<LoginResponse>?>
    suspend fun signUp(signUpQuery: SignUpQuery): Flow<Resource<User>?>
    suspend fun searchFlights(searchQuery: FlightSearchQuery): Flow<Resource<TripsResponse>?>
    suspend fun getUpcomingFlights(): Flow<Resource<FlightsResponse>?>
    suspend fun getTopFlights(): Flow<Resource<FlightsResponse>?>
    suspend fun getAllFLights(): Flow<Resource<TripsResponse>?>
    suspend fun getAds(): Flow<Resource<AdsResponse>?>
    suspend fun searchFlights(searchDatesQuery: FlightSearchDatesQuery): Flow<Resource<TripsResponse>?>
    suspend fun postAd(advertisement: Advertisement): Flow<Resource<Advertisement>?>


    /** Social **/
    suspend fun signInWithGoogle(acct: GoogleSignInAccount): Flow<Resource<AuthResult>?>
    suspend fun signInWithFacebook(token: AccessToken): Flow<Resource<AuthResult>?>


}