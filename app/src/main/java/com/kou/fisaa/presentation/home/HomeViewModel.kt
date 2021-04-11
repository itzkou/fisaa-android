package com.kou.fisaa.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.FlightsResponse
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel() {


    private val _upcomingFlightsResponse = MutableLiveData<Resource<FlightsResponse>>()
    val upcomingFlightsResponse = _upcomingFlightsResponse

    private val _topFlightsResponse = MutableLiveData<Resource<FlightsResponse>>()
    val topFlightsResponse = _topFlightsResponse




    fun getUpcomingFlights() {
        viewModelScope.launch {
            repository.getUpcomingFlights().collect { response ->
                response?.let {
                    _upcomingFlightsResponse.value = response
                }

            }
        }
    }

    fun getTopFlights() {
        viewModelScope.launch {
            repository.getTopFlights().collect { response ->
                response?.let {
                    _topFlightsResponse.value = response
                }

            }
        }
    }


}