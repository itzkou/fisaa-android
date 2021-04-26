package com.kou.fisaa.data.preferences

import kotlinx.coroutines.flow.Flow

interface PrefsStoreAbstraction {

    fun isNightMode(): Flow<Boolean>
    fun getId(): Flow<String?>

    suspend fun setNightMode()
    suspend fun setId(id: String)
}