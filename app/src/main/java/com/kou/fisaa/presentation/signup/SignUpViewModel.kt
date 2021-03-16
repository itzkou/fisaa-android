package com.kou.fisaa.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SignUpViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) : ViewModel() {
    private val _signUpResponse = MutableLiveData<Resource<User>>()
    val signupResponse = _signUpResponse

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