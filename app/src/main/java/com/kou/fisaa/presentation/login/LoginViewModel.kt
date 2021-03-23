package com.kou.fisaa.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.entities.SignUpQuery
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.repository.FisaaRepository
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {
    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    private val _googleResponse = MutableLiveData<Resource<AuthResult>>()
    private val _facebookResponse = MutableLiveData<Resource<AuthResult>>()
    private val _signUpResponse = MutableLiveData<Resource<User>>()
    val signupResponse = _signUpResponse
    val loginResponse = _loginResponse
    val googleResponse = _googleResponse
    val facebookResponse = _facebookResponse


    fun fetchLoginResponse(loginQuery: LoginQuery) {
        viewModelScope.launch {
            repository.login(loginQuery).collect {
                it?.let {
                    _loginResponse.value = it
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
    fun signUp(signUpQuery: SignUpQuery) {
        viewModelScope.launch {
            repository.signUp(signUpQuery).collect {
                it?.let {
                    _signUpResponse.value = it
                }
            }
        }
    }
}