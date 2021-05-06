package com.kou.fisaa.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.preferences.PrefsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefsStore: PrefsStore,
) : ViewModel() {
    private val _authResult = MutableLiveData<Boolean>()
    val authResult = _authResult

    val userId = prefsStore.getId()
    val fireToken = prefsStore.getFireToken()


    fun checkSession() {
        viewModelScope.launch {
            userId.collect { id ->
                fireToken.collect { uid ->
                    _authResult.value = !(id == null || uid == null)

                }
            }

        }
    }


}