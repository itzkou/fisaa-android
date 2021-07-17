package com.kou.fisaa.presentation.chat


import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
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
    val imageUrl: MutableLiveData<String> =
        MutableLiveData()  //Using Livedata with emit inside a task doesn't subscribe to observer
    val uploadTask: MutableLiveData<Resource<UploadTask.TaskSnapshot>> = MutableLiveData()
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


