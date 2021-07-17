package com.kou.fisaa.presentation.chat

import androidx.lifecycle.*
import com.kou.fisaa.data.entities.AdsResponse
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class BottomSheetAdsViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore
) : ViewModel() {

    val userId = prefsStore.getId().asLiveData()
    var myAds: LiveData<Resource<AdsResponse>> = userId.switchMap { id ->
        liveData {
            if (id != null) {
                repository.getMyAds(id).collect { resAds ->
                    resAds?.let {
                        emit(it)
                    }
                }
            }
        }
    }





}