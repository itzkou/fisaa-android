package com.kou.fisaa.data.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.*
import com.kou.fisaa.data.firestore.FirestoreRemote
import com.kou.fisaa.data.local.adLocalManager.AdLocalManager
import com.kou.fisaa.data.local.authLocalManager.AuthLocalManager
import com.kou.fisaa.data.local.flightLocalManager.FlightLocalManager
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class FisaaRepository @Inject constructor(
    private val remote: FisaaRemote,
    private val authLocalManager: AuthLocalManager,
    private val flightLocalManager: FlightLocalManager,
    private val adLocalManager: AdLocalManager,
    private val firestore: FirestoreRemote,
    private val ioDispatcher: CoroutineDispatcher
) : FisaaRepositoryAbstraction {

    override suspend fun login(loginQuery: LoginQuery): Flow<Resource<LoginResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.login(loginQuery)
            emit(response)

        }.flowOn(ioDispatcher)// TODO viewmodel calls dispatcher
    }

    override suspend fun signUp(signUpQuery: SignUpQuery): Flow<Resource<User>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.signUp(signUpQuery)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun searchFlights(searchQuery: FlightSearchQuery): Flow<Resource<TripsResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.searchFlights(searchQuery)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun searchFlights(searchDatesQuery: FlightSearchDatesQuery): Flow<Resource<TripsResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.searchFlights(searchDatesQuery)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun getUpcomingFlights(): Flow<Resource<FlightsResponse>?> {
        return flow {

            emit(getUpcomingFlightsCached())
            emit(Resource.loading())
            val response = remote.getUpcomingFlights()
            if (response.status == Resource.Status.SUCCESS) {
                response.data?.flights?.let { flights ->
                    flightLocalManager.deleteAll()
                    flightLocalManager.insertAll(flights)
                }
            }

            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun getTopFlights(): Flow<Resource<FlightsResponse>?> {
        return flow {
            // emit(getTopFlightsCached())  //TODO null exception Room _id not exist in sortBycount API
            emit(Resource.loading())
            val response = remote.getTopFlights()
            /* if (response.status == Resource.Status.SUCCESS) {
                 response.data?.flights?.let { flights ->
                     flightLocalManager.deleteAll(flights)
                     flightLocalManager.insertAll(flights)
                 }
             }*/
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAllFLights(): Flow<Resource<TripsResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.getALlFlights()
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAds(): Flow<Resource<AdsResponse>?> {
        return flow {

            emit(getAdsCached())
            val response = remote.getAds()
            if (response.status == Resource.Status.SUCCESS) {
                response.data?.ads?.let { ads ->
                    adLocalManager.deleteAll()
                    adLocalManager.insertAll(ads)

                }
            }
            emit(response)

        }.flowOn(ioDispatcher)
    }

    override suspend fun postAd(
        advertisement: AdsQuery
    ): Flow<Resource<AdsQuery>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.postAd(
                advertisement
            )
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun postParcel(
        partMap: Map<String, RequestBody>, file: MultipartBody.Part
    ): Flow<Resource<Parcel>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.postParcel(partMap, file)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun signInWithGoogle(acct: GoogleSignInAccount): Flow<Resource<AuthResult>?> {
        return flow {
            emit(Resource.loading())
            val response = firestore.signInWithGoogle(acct)
            emit(Resource.success(response))
        }.catch {
            // If exception is thrown, emit failed state along with message.
            emit(Resource.error(it.message.toString()))
        }.flowOn(ioDispatcher)
    }

    override suspend fun signInWithFacebook(token: AccessToken): Flow<Resource<AuthResult>?> {
        return flow {
            emit(Resource.loading())
            val response = firestore.signInWithFacebook(token)
            emit(Resource.success(response))
        }.catch {
            emit(Resource.error(it.message.toString()))
        }.flowOn(ioDispatcher)
    }

    /****  Local fetching ***/
    private fun getUpcomingFlightsCached(): Resource<FlightsResponse>? =
        flightLocalManager.getAll()?.let { flights ->
            Resource.success(FlightsResponse(flights))
        }

    private fun getTopFlightsCached(): Resource<FlightsResponse>? =
        flightLocalManager.getAll()?.let { flights ->
            Resource.success(FlightsResponse(flights))
        }

    private fun getAdsCached(): Resource<AdsResponse>? =
        adLocalManager.getAll()?.let { ads ->
            Resource.success(AdsResponse(ads))
        }

}