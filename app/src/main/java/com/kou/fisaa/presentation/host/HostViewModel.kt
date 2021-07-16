package com.kou.fisaa.presentation.host

import androidx.lifecycle.*
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
    val userPhoto: LiveData<String?> = userId.switchMap { id ->
        liveData {
            if (id != null) {
                repository.getUser(id).collect { resUser ->
                    resUser?.data.let { user ->
                        emit(user?.image)
                    }
                }
            }
        }
    }

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


}


