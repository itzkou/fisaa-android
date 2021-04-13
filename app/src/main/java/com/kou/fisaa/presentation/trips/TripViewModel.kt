package com.kou.fisaa.presentation.trips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.FlightSearchQuery
import com.kou.fisaa.data.entities.FlightsResponse
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {
    private val _flightsSearchResponse = MutableLiveData<Resource<FlightsResponse>>()
    val flightSearchResponse = _flightsSearchResponse

    fun searchFlights(searchQuery: FlightSearchQuery) {
        viewModelScope.launch {
            repository.searchFlights(searchQuery).collect { response ->
                response?.let {
                    _flightsSearchResponse.value = response
                }

            }
        }
    }

}