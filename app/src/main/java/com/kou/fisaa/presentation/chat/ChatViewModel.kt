package com.kou.fisaa.presentation.chat


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.kou.fisaa.data.entities.Message
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
    val userId = prefsStore.getId().asLiveData()
    private val _hasBeenSent =
        MutableLiveData<Resource<DocumentReference>>() //TODO  search viewmodel encapsulation
    val hasBeenSent = _hasBeenSent
    private val _msg = MutableLiveData<Resource<Message>>()
    val msg = _msg
    val userPhoto = MutableLiveData<String?>()


    fun sendMsg(msg: Message) {

        viewModelScope.launch {
            repository.sendMsg(msg).collect { resource ->
                resource?.let {
                    _hasBeenSent.value = it

                }
            }

        }

    }

    suspend fun listenMsgs(fromId: String, toId: String) {
        repository.listenMsgs(fromId, toId).collect { resource ->
            resource?.let {
                _msg.value = it

            }
        }


    }

    fun getImage() {
        viewModelScope.launch {
            prefsStore.getId().collect { userIdRes ->
                userIdRes?.let { id ->
                    repository.getUser(id).collect { userRes ->
                        userRes?.let {
                            userPhoto.value = it.data?.image
                        }
                    }
                }

            }

        }
    }


}


