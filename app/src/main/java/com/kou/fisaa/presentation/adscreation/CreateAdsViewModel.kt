package com.kou.fisaa.presentation.adscreation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.AdsQuery
import com.kou.fisaa.data.entities.Parcel
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateAdsViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    prefsStore: PrefsStore
) :
    ViewModel() {
    private val _adCreationResponse = MutableLiveData<Resource<AdsQuery>>()
    val adCreationResponse = _adCreationResponse
    private val _parcelCreationResponse = MutableLiveData<Resource<Parcel>>()
    val parcelCreationResponse = _parcelCreationResponse
    val userId = prefsStore.getId().asLiveData()


    fun postAd(advertisement: AdsQuery) {
        viewModelScope.launch {
            repository.postAd(advertisement).collect { response ->
                response?.let {
                    _adCreationResponse.value = response
                }

            }
        }
    }

    fun postParcel(body: RequestBody) {
        viewModelScope.launch {
            repository.postParcel(body).collect { response ->
                response?.let {
                    _parcelCreationResponse.value = response
                }
            }
        }
    }

}