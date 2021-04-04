package com.kou.fisaa.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(content: Context) {


    // Create the dataStore and give it a name same as shared preferences
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")
    private val mDataStore: DataStore<Preferences> = content.dataStore

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val USER_ID = stringPreferencesKey("USER_ID")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys

    suspend fun storeUser(id: String) {
        mDataStore.edit { preferences ->
            // here it refers to the preferences we are editing
            preferences[USER_ID] = id
            //  preferences[USER_IMAGE] = imageUrl


        }
    }

    // Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine


    val userIdFlow: Flow<String> = mDataStore.data.map {
        it[USER_ID] ?: ""
    }


}