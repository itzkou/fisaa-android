package com.kou.fisaa.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kou.fisaa.data.entities.SignUpQuery
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore,
    private val auth: FirebaseAuth,
) : ViewModel() {
    private val fireToken = auth.currentUser?.uid
    private val _signUpResponse = MutableLiveData<Resource<User>>()
    val signupResponse = _signUpResponse
    private val _fireSignUpResponse = MutableLiveData<Resource<AuthResult>>()
    val fireSignUpResponse = _fireSignUpResponse


    fun setId(id: String) {
        viewModelScope.launch {
            prefsStore.setId(id)
        }
    }

    fun setFireToken() {
        viewModelScope.launch {
            fireToken?.let {
                prefsStore.setFireToken(fireToken)
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

    fun signUpFirebase(email: String, password: String) {
        viewModelScope.launch {
            repository.register(email, password).collect {
                it?.let {
                    _fireSignUpResponse.value = it
                }
            }
        }
    }
}