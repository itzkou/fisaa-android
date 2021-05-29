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
    private val _msgs = MutableLiveData<Resource<List<Message>>>()
    val msgs = _msgs

    fun sendMsg(msg: Message) {

        viewModelScope.launch {
            repository.sendMsg(msg).collect { resource ->
                resource?.let {
                    hasBeenSent.value = it

                }
            }

        }

    }

    fun listenMsgs(fromId: String, toId: String) {
        viewModelScope.launch {
            repository.listenMsgs(fromId, toId).collect {
                it?.let {
                    _msgs.value = it

                }
            }
        }
    }


}

