package com.kou.fisaa.presentation.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {


    fun sendMsg(text: String) {

    }

    fun listenMsg() {

    }
}