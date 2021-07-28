package com.kou.fisaa.presentation.chat.chatroom


import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.kou.fisaa.data.entities.AdsResponse
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore
) : ViewModel() {

    val hasBeenSent: MutableLiveData<Resource<DocumentReference>> = MutableLiveData()
    val imageUrl: MutableLiveData<String> = MutableLiveData()
    val uploadTask: MutableLiveData<Resource<UploadTask.TaskSnapshot>> = MutableLiveData()
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
    val chosenAdToModify = MutableLiveData<Advertisement>()
    val myAds: LiveData<Resource<AdsResponse>> = userId.switchMap { id ->
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
    val storage = Firebase.storage.reference
    var other: LiveData<Resource<User>> = MutableLiveData()
    var msg: LiveData<Resource<Message>> = MutableLiveData()


    suspend fun persistImageFirestore(imageUri: Uri) {

        repository.uploadParcelImage(imageUri).collect { restaskSnapshot ->
            restaskSnapshot?.data?.let { taskSnapshot ->
                taskSnapshot.task.addOnSuccessListener {
                    storage.child("parcels")
                        .child(imageUri.lastPathSegment!!)
                        .downloadUrl
                        .addOnSuccessListener { url ->
                            imageUrl.value = url.toString()
                        }
                }
                taskSnapshot.task.addOnFailureListener {
                    Log.d("fireStorage Exception", it.message.toString())
                }

            }
            uploadTask.value = restaskSnapshot
        }

    }

    suspend fun listenMsgs(toId: String) {
        msg = userId.switchMap { id ->
            liveData {
                if (id != null)
                    repository.listenMsgs(id, toId).collect { resource ->
                        resource?.let {
                            emit(it)
                        }

                    }
            }
        }

    }

    suspend fun getOtherUser(id: String) {
        other = liveData {
            repository.getUser(id).collect { resource ->
                resource?.let {
                    emit(it)
                }

            }
        }

    }

    fun getAd(id: String) {
        viewModelScope.launch {
            repository.getAd(id).collect { resAd ->
                resAd?.data?.let { adv ->
                    chosenAdToModify.value = adv
                }
            }
        }
    }

    fun sendTransaction(idAd: String, toId: String) {
        viewModelScope.launch {
            repository.getAd(idAd).collect { resAd ->
                if (resAd != null) {
                    resAd.data?.let { advertisement ->
                        /** persisting transaction/ad as firestore msg **/
                        user.value?.data?.let { user ->
                            val msg = Message(
                                user._id,
                                toId,
                                "${user.firstName} veut envoyer un produit",
                                user.image ?: "",
                                user.firstName,
                                "",
                                advertisement,
                                System.currentTimeMillis() / 1000
                            )
                            sendMsg(msg)
                        }

                    }

                }
            }
        }
    }

    fun sendMsg(msg: Message) {                 // when you use LiveDataScope with Livedata the flow won't collect I wonder why
        viewModelScope.launch {
            Log.i(
                "TAGsendMsg:", "sendMsg"
            )
            repository.sendMsg(msg).collect { resource ->
                resource?.let {
                    hasBeenSent.value = it
                }
            }

        }


    }


}


