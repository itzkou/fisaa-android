package com.kou.fisaa.presentation.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
    private val _transactions = MutableLiveData<Resource<List<Message>>>()
    val userId = prefsStore.getId().asLiveData()
    val transactions = _transactions

    @ExperimentalCoroutinesApi
    fun listenTransactions(fromId: String) {
        viewModelScope.launch {
            repository.listenTransactions(fromId).collect {
                it?.let {
                    _transactions.value = it
                }
            }

        }
    }
}
