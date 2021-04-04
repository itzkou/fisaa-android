package com.kou.fisaa.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.FlightSearchQuery
import com.kou.fisaa.data.entities.FlightSearchResponse
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {

    private val _flightSearchResponse = MutableLiveData<Resource<FlightSearchResponse>>()
    val flightSearchResponse = _flightSearchResponse


    fun searchFlights(searchQuery: FlightSearchQuery) {
        viewModelScope.launch {
            repository.searchFlights(searchQuery).collect { response ->
                response?.let {
                    _flightSearchResponse.value = response
                }

            }
        }
    }

}