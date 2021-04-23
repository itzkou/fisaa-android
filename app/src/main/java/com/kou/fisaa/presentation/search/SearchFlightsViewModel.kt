package com.kou.fisaa.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.FlightSearchDatesQuery
import com.kou.fisaa.data.entities.TripsResponse
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFlightsViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {

    private val _tripsResponse = MutableLiveData<Resource<TripsResponse>>()
    val tripsResponse = _tripsResponse

    fun searchFlightsWithDates(searchQuery: FlightSearchDatesQuery) {
        viewModelScope.launch {
            repository.searchFlights(searchQuery).collect { response ->
                response?.let {
                    _tripsResponse.value = response
                }

            }
        }
    }
}