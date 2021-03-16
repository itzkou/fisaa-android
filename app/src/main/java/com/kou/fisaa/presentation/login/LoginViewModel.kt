package com.kou.fisaa.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.repository.FisaaRepository
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) : ViewModel() {
    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    private val _googleResponse = MutableLiveData<Resource<AuthResult>>()
    val loginResponse = _loginResponse
    val googleResponse = _googleResponse


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
}