package com.kou.fisaa.presentation.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.preferences.PrefsStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val prefsStore: PrefsStore
) : ViewModel() {
    val darkThemeEnabled = prefsStore.isNightMode().asLiveData()
    val userId = prefsStore.getId().asLiveData()

    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.setNightMode()
        }
    }

}