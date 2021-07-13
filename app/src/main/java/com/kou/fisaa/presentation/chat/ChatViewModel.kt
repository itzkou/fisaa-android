package com.kou.fisaa.presentation.chat


import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentReference
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
    private val _msg = MutableLiveData<Resource<Message>>()
    val hasBeenSent = _hasBeenSent
    val msg = _msg
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

    // todo nested flow that rely on each other
    /* fun getUser(id: String) {
         viewModelScope.launch {
             repository.getUser(id).collect { resUser ->
                 resUser?.let { me ->
                     user.value = me.data
                 }
             }
         }

     }*/
}


