package com.kou.fisaa.presentation.adscreation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kou.fisaa.data.entities.FlightsResponse
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateAdsViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {
    private val _adCreationResponse = MutableLiveData<Resource<FlightsResponse>>()
    val adCreationResponse = _adCreationResponse

}