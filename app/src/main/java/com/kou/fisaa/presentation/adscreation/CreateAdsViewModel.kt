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
import com.kou.fisaa.utils.createPartFromString
import com.kou.fisaa.utils.prepareImageFilePart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.set

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

    fun postParcel(partMap: Map<String, RequestBody>, file: MultipartBody.Part) {
        viewModelScope.launch {
            repository.postParcel(partMap, file).collect { response ->
                response?.let {
                    _parcelCreationResponse.value = response
                }
            }

        }
    }

    fun prepareParcel(
        file: File,
        bonus: String,
        description: String,
        dimension: String,
        parcelType: String,
        weight: String
    ) {
        val image = prepareImageFilePart("photo", file)
        val map: HashMap<String, RequestBody> = HashMap()

        map["bonus"] = createPartFromString(bonus)
        map["description"] = createPartFromString(description)
        map["dimension"] = createPartFromString(dimension)
        map["parcelType"] = createPartFromString(parcelType)
        map["weight"] = createPartFromString(weight)

        postParcel(map, image)


    }

}