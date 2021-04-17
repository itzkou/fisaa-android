package com.kou.fisaa.data.local.adLocalManager

import com.kou.fisaa.data.entities.Advertisement
import javax.inject.Inject

class AdLocalManager @Inject constructor(private val adDao: AdDao) {
    fun getAll(): List<Advertisement>? {
        return adDao.getAll()
    }

    fun insertAll(ads: List<Advertisement>) {
        return adDao.insertAll(ads)
    }


    fun deleteAll() {
        return adDao.deleteAll()
    }
}