package com.kou.fisaa.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.firestore.FirestoreRemote
import com.kou.fisaa.data.local.FisaaDao
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FisaaRepository @Inject constructor(
    private val remote: FisaaRemote,
    private val local: FisaaDao,
    private val firestore: FirestoreRemote
) {

    suspend fun login(loginQuery: LoginQuery): Flow<Resource<LoginResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.login(loginQuery)
            emit(response)

        }.flowOn(Dispatchers.IO)
    }

    suspend fun signInWithGoogle(acct: GoogleSignInAccount): Flow<Resource<AuthResult>?> {
        return flow {
            emit(Resource.loading())
            val response = firestore.signInWithGoogle(acct)
            emit(Resource.success(response))
        }.catch {
            // If exception is thrown, emit failed state along with message.
            emit(Resource.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }


}