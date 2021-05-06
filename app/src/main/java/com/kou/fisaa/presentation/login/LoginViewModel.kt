package com.kou.fisaa.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction, private val prefsStore: PrefsStore
) :
    ViewModel() {
    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    private val _googleResponse = MutableLiveData<Resource<AuthResult>>()
    private val _facebookResponse = MutableLiveData<Resource<AuthResult>>()
    val loginResponse = _loginResponse
    val googleResponse = _googleResponse
    val facebookResponse = _facebookResponse


    fun setId(id: String) {
        viewModelScope.launch {
            prefsStore.setId(id)
        }
    }

    fun setFireToken(uid: String) {
        viewModelScope.launch {
            prefsStore.setFireToken(uid)
        }
    }


    fun fetchLoginResponse(loginQuery: LoginQuery) {
        viewModelScope.launch {
            repository.login(loginQuery).collect { response ->
                response?.let {
                    _loginResponse.value = response

                }

            }
        }
    }


    fun signInWithGoogle(acct: GoogleSignInAccount) {
        viewModelScope.launch {
            repository.signInWithGoogle(acct).collect {
                it?.let {
                    _googleResponse.value = it
                }
            }
        }
    }

    fun signInWithFacebook(token: AccessToken) {
        viewModelScope.launch {
            repository.signInWithFacebook(token).collect {
                it?.let {
                    _facebookResponse.value = it
                }
            }
        }
    }

}