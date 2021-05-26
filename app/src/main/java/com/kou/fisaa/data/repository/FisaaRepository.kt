package com.kou.fisaa.data.repository

import android.net.Uri
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.storage.UploadTask
import com.kou.fisaa.data.entities.*
import com.kou.fisaa.data.firestore.FirestoreRemote
import com.kou.fisaa.data.local.adLocalManager.AdLocalManager
import com.kou.fisaa.data.local.authLocalManager.AuthLocalManager
import com.kou.fisaa.data.local.flightLocalManager.FlightLocalManager
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
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
    /*****   Firebase ***/

    override suspend fun login(email: String, password: String): Flow<Resource<AuthResult>?> {
        return flow {
            emit(Resource.loading())
            val response = firestore.login(email, password)
            emit(Resource.success(response))
        }.catch {
            emit(Resource.error(it.message.toString()))

        }.flowOn(ioDispatcher)
    }

    override suspend fun register(email: String, password: String): Flow<Resource<AuthResult>?> {
        return flow {
            emit(Resource.loading())
            val response = firestore.register(email, password)
            emit(Resource.success(response))
        }.catch {
            emit(Resource.error(it.message.toString()))

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

    /*****   Firestore ***/
    override suspend fun registerFirestore(user: User): Flow<Resource<DocumentReference>?> {
        return flow {
            val userRef = firestore.registerFirestore(user)
            emit(Resource.success(userRef))
        }.catch {
            emit(Resource.error(it.message.toString()))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getUsers(): Flow<Resource<List<User>>?> {
        return flow {
            val snapshot = firestore.getUsers()
            val users = snapshot.toObjects(User::class.java)
            emit(Resource.success(users))
        }.catch {
            emit(Resource.error(it.message.toString()))
        }.flowOn(ioDispatcher)
    }

    override suspend fun sendMsg(msg: Message): Flow<Resource<DocumentReference>?> {
        return flow {
            val msgRef = firestore.sendMsg(msg)
            emit(Resource.success(msgRef))
        }.catch {
            emit(Resource.error(it.message.toString()))
        }.flowOn(ioDispatcher)
    }

    @ExperimentalCoroutinesApi
    override suspend fun listenMsgs(fromId: String, toId: String): Flow<Resource<List<Message>>?> {
        return channelFlow {
            val subscription =
                firestore.listenMsgs(fromId, toId).addSnapshotListener { snapshot, error ->
                    if (error != null)
                        channel.offer(Resource.error(error.toString()))
                    else if (snapshot != null) {
                        val msgs = snapshot.toObjects(Message::class.java)
                        channel.offer(Resource.success(msgs))
                    }

                }

            // 3) Don't close the stream of data, keep it open until the consumer
            // stops listening or the API calls onCompleted or onError.
            // When that happens, cancel the subscription to the 3P library
            awaitClose { subscription.remove() }
        }
    }

    /*** Storage ***/
    override suspend fun uploadParcelImage(imageUri: Uri): Flow<Resource<UploadTask.TaskSnapshot>?> {
        return flow {
            val snapshot = firestore.uploadParcelImage(imageUri)
            emit(Resource.success(snapshot))
        }.catch {
            emit(Resource.error(it.message.toString()))
        }.flowOn(ioDispatcher)
    }

    /*****   Remote  ***/
    override suspend fun signUp(signUpQuery: Map<String, RequestBody>): Flow<Resource<User>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.signUp(signUpQuery)
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun login(loginQuery: LoginQuery): Flow<Resource<LoginResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.login(loginQuery)
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
            // emit(getTopFlightsCached())  //TODO null exception Room _id not exist  because of   sortByCount mongo
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

    override suspend fun postAd(advertisement: AdsQuery): Flow<Resource<AdsQuery>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.postAd(
                advertisement
            )
            emit(response)
        }.flowOn(ioDispatcher)
    }

    override suspend fun postParcel(partMap: Map<String, RequestBody>): Flow<Resource<Parcel>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.postParcel(partMap)
            emit(response)
        }.flowOn(ioDispatcher)
    }


    /****  Room ***/
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