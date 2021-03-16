package com.kou.fisaa.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.entities.SignUpQuery
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.firestore.FirestoreRemote
import com.kou.fisaa.data.local.authLocalManager.AuthLocalManager
import com.kou.fisaa.data.local.authLocalManager.FisaaDao
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FisaaRepository  constructor(
    private val remote: FisaaRemote,
    private val local: AuthLocalManager,
    private val firestore: FirestoreRemote
) :FisaaRepositoryAbstraction{

    override suspend fun login(loginQuery: LoginQuery): Flow<Resource<LoginResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.login(loginQuery)
            emit(response)

        }.flowOn(Dispatchers.IO)// viewmodel calls dispatcher
    }

    override suspend fun signUp(signUpQuery: SignUpQuery):Flow<Resource<User>?>{
        return flow {
            emit(Resource.loading())
            val response = remote.signUp(signUpQuery)
            emit(response)
        }.flowOn(Dispatchers.IO) // Dispatchers are called from viewmodel
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


}