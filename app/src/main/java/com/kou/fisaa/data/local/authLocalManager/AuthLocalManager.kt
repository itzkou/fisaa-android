package com.kou.fisaa.data.local.authLocalManager

import androidx.room.Query
import com.kou.fisaa.data.entities.User
import javax.inject.Inject

class AuthLocalManager@Inject constructor(private  val fisaaDao: FisaaDao) {

    /** Get User **/
    fun getUser(id: Int): User{
        return  fisaaDao.getUser(id)
    }
}