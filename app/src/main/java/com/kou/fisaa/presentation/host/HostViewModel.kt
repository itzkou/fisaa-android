package com.kou.fisaa.presentation.host

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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
    val userPhoto = MutableLiveData<String?>()

    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.setNightMode()
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


    fun getImage() {
        viewModelScope.launch {
            prefsStore.getId().collect { userIdRes ->
                userIdRes?.let { id ->
                    repository.getUser(id).collect { userRes ->
                        userRes?.let {
                            userPhoto.value = it.data?.image
                        }
                    }
                }

            }

        }
    }
}


