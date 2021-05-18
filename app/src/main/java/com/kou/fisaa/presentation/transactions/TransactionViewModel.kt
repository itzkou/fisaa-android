package com.kou.fisaa.presentation.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.repository.FisaaRepository
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: FisaaRepository) :
    ViewModel() {
    private val _users = MutableLiveData<Resource<List<User>>>()
    val users = _users

    fun getUsers() {
        viewModelScope.launch {
            repository.getUsers().collect {
                it?.let {
                    _users.value = it
                }
            }

        }
    }
}
