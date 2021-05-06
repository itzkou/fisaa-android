package com.kou.fisaa.data.preferences

import kotlinx.coroutines.flow.Flow

interface PrefsStoreAbstraction {

    fun isNightMode(): Flow<Boolean>
    fun getId(): Flow<String?>
    fun getFireToken(): Flow<String?>


    suspend fun setNightMode()
    suspend fun setId(id: String)
    suspend fun setFireToken(id: String)
}