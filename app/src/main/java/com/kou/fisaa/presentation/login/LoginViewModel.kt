package com.kou.fisaa.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore,
    private val googleSignInClient: GoogleSignInClient,
    private val callbackManager: CallbackManager
) :
    ViewModel() {
    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    private val _fireLoginResponse = MutableLiveData<Resource<AuthResult>>()
    private val _googleResponse = MutableLiveData<Resource<AuthResult>>()
    private val _facebookResponse = MutableLiveData<Resource<AuthResult>>()
    private val _firestoreSignUpResponse = MutableLiveData<Resource<DocumentReference>>()
    val firestoreSignUpResponse = _firestoreSignUpResponse
    val loginResponse = _loginResponse
    val fireLoginResponse = _fireLoginResponse
    val googleResponse = _googleResponse
    val facebookResponse = _facebookResponse


    fun setId(id: String) {
        viewModelScope.launch {
            prefsStore.setId(id)
        }
    }

    fun setFireToken(id: String) {
        viewModelScope.launch {

            prefsStore.setFireToken(id)

        }
    }

    fun fetchLoginResponse(loginQuery: LoginQuery) {
        viewModelScope.launch {
            repository.login(loginQuery).collect { response ->
                response?.let {
                    _loginResponse.value = response


                }

            }
        }
    }

    //TODO fix this
    fun loginWithFirebase(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect {
                it?.let {
                    _fireLoginResponse.value = it
                }
            }
        }
    }

    fun getGoogleClient() = googleSignInClient
    fun signInWithGoogle(acct: GoogleSignInAccount) {
        viewModelScope.launch {
            repository.signInWithGoogle(acct).collect {
                it?.let {
                    _googleResponse.value = it
                }
            }
        }
    }

    fun getCallBackMg() = callbackManager
    fun signInWithFacebook(token: AccessToken) {
        viewModelScope.launch {
            repository.signInWithFacebook(token).collect {
                it?.let {
                    _facebookResponse.value = it
                }
            }
        }
    }

    fun signUpFirestore(user: User) {
        viewModelScope.launch {
            repository.registerFirestore(user).collect {
                it?.let {
                    firestoreSignUpResponse.value = it
                }
            }
        }
    }

}