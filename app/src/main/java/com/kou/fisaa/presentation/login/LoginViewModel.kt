package com.kou.fisaa.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kou.fisaa.data.repository.FisaaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FisaaRepository):ViewModel() {

    var loginResponse = MutableLiveData<String> ()
    init {
        loginResponse.value="hi kou"
    }
}