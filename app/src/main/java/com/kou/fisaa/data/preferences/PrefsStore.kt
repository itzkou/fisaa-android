package com.kou.fisaa.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore("user_prefs")

class PrefsStore @Inject constructor(appContext: Context) : PrefsStoreAbstraction {

    private val dataStore = appContext.dataStore
    override fun isNightMode() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.NIGHT_MODE_KEY] ?: false }

    override fun getId() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.USER_ID] }

    override fun getFireToken() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.USER_FIRE_TOKEN] }

    override suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun setNightMode() {
        dataStore.edit {
            it[PreferencesKeys.NIGHT_MODE_KEY] = !(it[PreferencesKeys.NIGHT_MODE_KEY] ?: false)
        }
    }

    override suspend fun setId(id: String) {
        dataStore.edit {
            it[PreferencesKeys.USER_ID] = id
        }
    }

    override suspend fun setFireToken(id: String) {
        dataStore.edit {
            it[PreferencesKeys.USER_FIRE_TOKEN] = id
        }
    }

    private object PreferencesKeys {
        val NIGHT_MODE_KEY = booleanPreferencesKey("dark_theme_enabled")
        val USER_ID = stringPreferencesKey("id")
        val USER_FIRE_TOKEN = stringPreferencesKey("token")
    }
}