package com.kou.fisaa.presentation.adscreation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kou.fisaa.data.entities.AdsQuery
import com.kou.fisaa.data.entities.Parcel
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.createPartFromString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class CreateAdsViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore
) :
    ViewModel() {
    private val _adCreationResponse = MutableLiveData<Resource<AdsQuery>>()
    val adCreationResponse = _adCreationResponse
    private val _parcelCreationResponse = MutableLiveData<Resource<Parcel>>()
    val parcelCreationResponse = _parcelCreationResponse
    val userId = prefsStore.getId().asLiveData()
    val storage = Firebase.storage.reference


    fun postAd(advertisement: AdsQuery) {
        viewModelScope.launch {
            repository.postAd(advertisement).collect { response ->
                response?.let {
                    _adCreationResponse.value = response
                }

            }
        }
    }

    fun postParcelImage(
        imageUri: Uri, bonus: String,
        description: String,
        dimension: String,
        parcelType: String,
        weight: String
    ) {

        viewModelScope.launch {
            repository.uploadParcelImage(imageUri).collect { resource ->
                resource?.data?.let { taskSnapshot ->
                    taskSnapshot.task.addOnSuccessListener {
                        storage.child("parcels")
                            .child(imageUri.lastPathSegment!!)
                            .downloadUrl
                            .addOnSuccessListener { url ->
                                prepareAndPostParcel(
                                    url.toString(),
                                    bonus,
                                    description,
                                    dimension,
                                    parcelType,
                                    weight
                                )
                            }
                    }
                    taskSnapshot.task.addOnFailureListener {
                        Log.d("fireStorage Exception", it.message.toString())
                    }
                }
            }
        }
    }


    fun prepareAndPostParcel(
        imageURL: String,
        bonus: String,
        description: String,
        dimension: String,
        parcelType: String,
        weight: String
    ) {
        val map: HashMap<String, RequestBody> = HashMap()

        map["bonus"] = createPartFromString(bonus)
        map["description"] = createPartFromString(description)
        map["dimension"] = createPartFromString(dimension)
        map["parcelType"] = createPartFromString(parcelType)
        map["weight"] = createPartFromString(weight)
        map["photo"] = createPartFromString(imageURL)

        viewModelScope.launch {
            repository.postParcel(map).collect { response ->
                response?.let {
                    _parcelCreationResponse.value = response
                }
            }

        }


    }


}