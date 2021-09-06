package com.kou.fisaa.presentation.profile.fragments

import androidx.lifecycle.*
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MyDetailsViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore
) : ViewModel() {
    val userId = prefsStore.getId().asLiveData()
    val user: LiveData<Resource<User>> = userId.switchMap { id ->
        liveData {
            if (id != null) {
                repository.getUser(id).collect { resUser ->
                    resUser?.let {
                        emit(it)
                    }
                }
            }
        }
    }

    fun showMyFlights() {

    }
}