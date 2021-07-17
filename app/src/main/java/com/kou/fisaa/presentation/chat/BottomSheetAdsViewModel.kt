package com.kou.fisaa.presentation.chat

import androidx.lifecycle.*
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.data.entities.User
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
    var myAds: LiveData<List<Advertisement>> = MutableLiveData()
    val userId = prefsStore.getId().asLiveData()
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


    fun getMyAds() {
        myAds =
            liveData {
            }
    }

}