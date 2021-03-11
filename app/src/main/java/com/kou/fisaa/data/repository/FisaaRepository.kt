package com.kou.fisaa.data.repository

import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.local.FisaaDao
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FisaaRepository @Inject constructor(
    private  val remote: FisaaRemote,
    private  val local :FisaaDao
) {

    suspend fun login(user:User):Flow<Resource<LoginResponse>?> {
        return flow {
            emit(Resource.loading())
            val response = remote.login(user)
            emit(response)

        }.flowOn(Dispatchers.IO)
    }



}