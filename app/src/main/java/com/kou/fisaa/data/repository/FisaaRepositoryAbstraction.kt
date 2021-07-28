package com.kou.fisaa.data.repository

import android.net.Uri
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.UploadTask
import com.kou.fisaa.data.entities.*
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface FisaaRepositoryAbstraction {


    /** Firebase **/
    suspend fun signInWithGoogle(acct: GoogleSignInAccount): Flow<Resource<AuthResult>?>
    suspend fun signInWithFacebook(token: AccessToken): Flow<Resource<AuthResult>?>
    suspend fun login(email: String, password: String): Flow<Resource<AuthResult>?>
    suspend fun register(email: String, password: String): Flow<Resource<AuthResult>?>
    suspend fun isUserExistsForEmail(email: String): Flow<Resource<SignInMethodQueryResult>?>


    /** Firestore **/
    suspend fun registerFirestore(user: FireUser): Flow<Resource<DocumentReference>?>
    suspend fun getUsers(): Flow<Resource<List<User>>?>
    suspend fun sendMsg(msg: Message): Flow<Resource<DocumentReference>?>
    suspend fun listenMsgs(fromId: String, toId: String): Flow<Resource<Message>?>
    suspend fun listenTransactions(toId: String): Flow<Resource<Message>?>
    // suspend fun updateParcelFirestore(advertisement: Advertisement): Flow<Resource<Task<Void>>>


    /*** Storage **/
    suspend fun uploadParcelImage(imageUri: Uri): Flow<Resource<UploadTask.TaskSnapshot>?>


    /*** Remote ***/
    suspend fun login(loginQuery: LoginQuery): Flow<Resource<LoginResponse>?>
    suspend fun signUp(signUpQuery: Map<String, RequestBody>): Flow<Resource<User>?>
    suspend fun getUser(id: String): Flow<Resource<User>?>
    suspend fun searchFlights(searchQuery: FlightSearchQuery): Flow<Resource<TripsResponse>?>
    suspend fun getUpcomingFlights(): Flow<Resource<FlightsResponse>?>
    suspend fun getTopFlights(): Flow<Resource<FlightsResponse>?>
    suspend fun getAllFLights(): Flow<Resource<TripsResponse>?>
    suspend fun getAds(): Flow<Resource<AdsResponse>?>
    suspend fun getAd(id: String): Flow<Resource<Advertisement>?>
    suspend fun getMyAds(id: String): Flow<Resource<AdsResponse>?>
    suspend fun searchFlights(searchDatesQuery: FlightSearchDatesQuery): Flow<Resource<TripsResponse>?>
    suspend fun searchAds(searchQuery: AdSearchQuery): Flow<Resource<AdsResponse>?>
    suspend fun postAd(advertisement: AdsQuery): Flow<Resource<AdsQuery>?>
    suspend fun postParcel(partMap: Map<String, RequestBody>): Flow<Resource<Parcel>?>
    suspend fun updateParcelRemote(
        parcelQuery: ParcelQuery,
        id: String
    ): Flow<Resource<ParcelUpdateResponse>?>


}