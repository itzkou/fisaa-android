package com.kou.fisaa.presentation.ads

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.AdsResponse
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdsViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {
    private val _adsResponse = MutableLiveData<Resource<AdsResponse>>()
    val adsResponse = _adsResponse


    fun getAds() {
        viewModelScope.launch {
            repository.getAds().collect { response ->
                _adsResponse.value = response

            }
        }
    }
}