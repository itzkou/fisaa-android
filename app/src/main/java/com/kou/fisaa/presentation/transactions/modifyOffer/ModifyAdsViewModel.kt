package com.kou.fisaa.presentation.transactions.modifyOffer

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kou.fisaa.data.entities.ParcelQuery
import com.kou.fisaa.data.entities.ParcelUpdateResponse
import com.kou.fisaa.data.repository.FisaaRepository
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ModifyAdsViewModel @Inject constructor(
    private val repository: FisaaRepository
) : ViewModel() {
    private val _goToChat = SingleLiveEvent<Unit>()
    val goToChat: LiveData<Unit> = _goToChat
    val storage = Firebase.storage.reference
    val imageUrl = MutableLiveData<String>()
    val parcelUpdateResponse = MutableLiveData<Resource<ParcelUpdateResponse>>()


    fun postParcelImage(imageUri: Uri) {
        viewModelScope.launch {
            repository.uploadParcelImage(imageUri).collect { resource ->
                resource?.data?.let { taskSnapshot ->
                    taskSnapshot.task.addOnSuccessListener {
                        storage.child("parcels")
                            .child(imageUri.lastPathSegment!!)
                            .downloadUrl
                            .addOnSuccessListener { url ->
                                imageUrl.value = url.toString()
                            }
                            .addOnFailureListener {
                                imageUrl.value = "Error uploading Image"
                            }
                    }
                    taskSnapshot.task.addOnFailureListener {
                        Log.d("fireStorage Exception", it.message.toString())
                    }
                }
            }
        }
    }

    fun updateParcel(parcelQuery: ParcelQuery, id: String) {
        viewModelScope.launch {
            repository.updateParcelRemote(parcelQuery, id).collect {
                it?.let {
                    parcelUpdateResponse.value = it
                }
            }
        }
    }

}