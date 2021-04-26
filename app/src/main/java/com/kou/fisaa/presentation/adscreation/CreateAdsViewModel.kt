package com.kou.fisaa.presentation.adscreation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAdsViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {
    private val _adCreationResponse = MutableLiveData<Resource<Advertisement>>()
    val adCreationResponse = _adCreationResponse

    fun postAd(advertisement: Advertisement) {
        viewModelScope.launch {
            repository.postAd(advertisement).collect { response ->
                response?.let {
                    _adCreationResponse.value = response
                }

            }
        }
    }

}