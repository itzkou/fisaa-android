package com.kou.fisaa.presentation.chat


import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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
    private val _hasBeenSent =
        MutableLiveData<Resource<DocumentReference>>() //TODO  search viewmodel encapsulation
    val hasBeenSent = _hasBeenSent
    var msg: LiveData<Resource<Message>> = MutableLiveData()
    var imageUrl: LiveData<Resource<String>> = MutableLiveData()
    val userId = prefsStore.getId().asLiveData()
    var other: LiveData<Resource<User>> = MutableLiveData()
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
    val storage = Firebase.storage.reference

    fun persistImageFirestore(imageUri: Uri) {
        viewModelScope.launch {
            repository.uploadParcelImage(imageUri).collect { resource ->
                resource?.data?.let { taskSnapshot ->
                    taskSnapshot.task.addOnSuccessListener {
                        storage.child("Chats")
                            .child(imageUri.lastPathSegment!!).downloadUrl.addOnSuccessListener { url ->
                                imageUrl = liveData {
                                    emit(Resource.loading())
                                    emit(Resource.success(url.toString()))
                                }
                            }.addOnFailureListener {

                                Log.d("fireStorage Exception", it.message.toString())
                            }
                    }
                }
            }
        }
    }


    fun sendMsg(msg: Message) {

        viewModelScope.launch {
            repository.sendMsg(msg).collect { resource ->
                resource?.let {
                    _hasBeenSent.value = it

                }
            }

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
}


