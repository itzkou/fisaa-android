package com.kou.fisaa.data.local.authLocalManager

import javax.inject.Inject

class AuthLocalManager @Inject constructor(private val authDao: AuthDao) {

    fun getUser(id: Int) = authDao.getUser(id)
}