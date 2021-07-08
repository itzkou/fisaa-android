package com.kou.fisaa.presentation.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val prefsStore: PrefsStore,
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val repository: FisaaRepository
) : ViewModel() {
    val darkThemeEnabled = prefsStore.isNightMode().asLiveData()
    val userId = prefsStore.getId().asLiveData()

    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.setNightMode()
        }
    }

    fun getUser() {
        viewModelScope.launch {
            repository.
        }
    }

    fun logout() {
        auth.signOut()
        googleSignInClient.signOut()
        LoginManager.getInstance().logOut()
        viewModelScope.launch {
            prefsStore.clearDataStore()

        }

    }


}