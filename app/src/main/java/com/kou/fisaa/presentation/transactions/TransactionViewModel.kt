package com.kou.fisaa.presentation.transactions

import androidx.lifecycle.*
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepository
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val prefsStore: PrefsStore,
    private val repository: FisaaRepository
) :
    ViewModel() {
    val userId = prefsStore.getId().asLiveData()
    var transaction: LiveData<Resource<Message>> = MutableLiveData()

    @ExperimentalCoroutinesApi
    fun listenTransactions() {
        viewModelScope.launch {
            transaction = userId.switchMap { id ->
                liveData {
                    if (id != null)
                        repository.listenTransactions(id).collect { resource ->
                            resource?.let {
                                emit(it)
                            }

                        }
                }
            }
        }
    }
}
