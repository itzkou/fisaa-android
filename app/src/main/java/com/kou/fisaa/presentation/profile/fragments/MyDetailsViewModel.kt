package com.kou.fisaa.presentation.profile.fragments

import androidx.lifecycle.*
import com.kou.fisaa.data.entities.PublishedAdvert
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDetailsViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore
) : ViewModel() {
    val userId = prefsStore.getId().asLiveData()
    val myFlights: MutableLiveData<List<PublishedAdvert>> = MutableLiveData()
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

    fun showMyFlights(id: String) {
        viewModelScope.launch {
            repository.showMyFlights(id).collect { resFlights ->
                resFlights?.let {
                    val flights = it.data?.publishedAdverts
                    flights?.let {
                        myFlights.value = it
                    }
                }
            }
        }
    }
}