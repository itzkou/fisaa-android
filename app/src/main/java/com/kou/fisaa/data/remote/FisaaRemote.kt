package com.kou.fisaa.data.remote

import com.kou.fisaa.data.entities.User
import com.kou.fisaa.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class FisaaRemote @Inject constructor(
        private val fisaaApi: FisaaApi
) : RetrofitSource() {

    suspend fun login(user: User) = getResult { fisaaApi.login(user) }
    //suspend fun getUser(id: Int) = getResult { fisaaApi.getUser(id) }


}