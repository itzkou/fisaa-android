package com.kou.fisaa.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.preferences.PrefsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefsStore: PrefsStore,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val darkThemeEnabled = prefsStore.isNightMode().asLiveData()
    val userId = prefsStore.getId().asLiveData()
    fun toggleNightMode() {
        viewModelScope.launch(ioDispatcher) {
            prefsStore.setNightMode()
        }
    }
}