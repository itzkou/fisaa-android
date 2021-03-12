package com.kou.fisaa.data.remote

import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.User
import javax.inject.Inject

class FisaaRemote @Inject constructor(
        private val fisaaApi: FisaaApi
) : RetrofitSource() {

    suspend fun login(loginQuery:LoginQuery) = getResource{ fisaaApi.login(loginQuery) }
    //suspend fun getUser(id: Int) = getResult { fisaaApi.getUser(id) }


}