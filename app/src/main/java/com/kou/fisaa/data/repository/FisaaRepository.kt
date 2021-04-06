package com.kou.fisaa.data.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.*
import com.kou.fisaa.data.firestore.FirestoreRemote
import com.kou.fisaa.data.local.authLocalManager.AuthLocalManager
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FisaaRepository constructor(
    private val remote: FisaaRemote,
    private val local: AuthLocalManager,
    private val firestore: FirestoreRemote
) : FisaaRepositoryAbstraction {

    override suspend fun login(loginQuery: LoginQuery): Flow<Resource<LoginResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.login(loginQuery)
            emit(response)

        }.flowOn(Dispatchers.IO)// TODO viewmodel calls dispatcher
    }

    override suspend fun signUp(signUpQuery: SignUpQuery): Flow<Resource<User>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.signUp(signUpQuery)
            emit(response)
        }.flowOn(Dispatchers.IO) // Dispatchers are called from viewmodel
    }

    override suspend fun searchFlights(searchQuery: FlightSearchQuery): Flow<Resource<FlightsResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.searchFlights(searchQuery)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUpcomingFlights(): Flow<Resource<FlightsResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.getUpcomingFlights()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTopFlights(): Flow<Resource<FlightsResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.getTopFlights()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun signInWithGoogle(acct: GoogleSignInAccount): Flow<Resource<AuthResult>?> {
        return flow {
            emit(Resource.loading())
            val response = firestore.signInWithGoogle(acct)
            emit(Resource.success(response))
        }.catch {
            // If exception is thrown, emit failed state along with message.
            emit(Resource.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun signInWithFacebook(token: AccessToken): Flow<Resource<AuthResult>?> {
        return flow {
            emit(Resource.loading())
            val response = firestore.signInWithFacebook(token)
            emit(Resource.success(response))
        }.catch {
            emit(Resource.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }


}