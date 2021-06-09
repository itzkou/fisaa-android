package com.kou.fisaa.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.kou.fisaa.data.entities.FireUser
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.data.entities.LoginResponse
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore,
    private val googleSignInClient: GoogleSignInClient,
    private val _callbackManager: CallbackManager
) :
    ViewModel() {
    private val _fisaaLoginResponse = MutableLiveData<Resource<LoginResponse>>()
    private val _firebaseLoginResponse = MutableLiveData<Resource<AuthResult>>()
    private val _googleResponse = MutableLiveData<Resource<AuthResult>>()
    private val _facebookResponse = MutableLiveData<Resource<AuthResult>>()
    private val _firestoreResponse = MutableLiveData<Resource<DocumentReference>>()
    val firestoreResponse = _firestoreResponse
    val fisaaLoginResponse = _fisaaLoginResponse
    val firebaseLoginResponse = _firebaseLoginResponse
    val googleResponse = _googleResponse
    val facebookResponse = _facebookResponse
    val callbackManager = _callbackManager


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


    fun fisaaLogin(loginQuery: LoginQuery) {
        viewModelScope.launch {
            repository.login(loginQuery).collect { response ->
                response?.let {
                    _fisaaLoginResponse.value = response
                }

            }
        }
    }

    fun loginWithFirebase(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect {
                it?.let {
                    _firebaseLoginResponse.value = it
                }
            }
        }
    }

    fun getGoogleClient() = googleSignInClient

    fun syncGoogleFirestore(account: GoogleSignInAccount, exists: Boolean) {
        viewModelScope.launch {

            if (!exists) {
                repository.signInWithGoogle(account).collect { resource ->
                    resource?.let {
                        val firebaseUser = it.data?.user
                        firebaseUser?.let { user ->

                            fisaaLogin(mkFisaaUser(user))
                            Transformations.map(_fisaaLoginResponse) { fisaaLoginResource ->
                                fisaaLoginResource?.let {
                                    if (fisaaLoginResource.status == Resource.Status.SUCCESS) {
                                        val fisaaUser = it.data?.data
                                        fisaaUser?.let {
                                            setId(fisaaUser._id)
                                            saveToFirestore(
                                                mkFirestoreUser(user, fisaaUser._id),
                                                this
                                            )
                                        }

                                    }

                                }
                            }/*
                            repository.login(mkFisaaUser(user)).collect { loginResource ->
                                loginResource?.let {
                                    if (loginResource.status == Resource.Status.SUCCESS) {
                                        val fisaaUser = it.data?.data
                                        fisaaUser?.let {
                                            setId(fisaaUser._id)
                                            saveToFirestore(
                                                mkFirestoreUser(user, fisaaUser._id),
                                                this
                                            )
                                        }

                                    }

                                }

                            }*/
                        }
                        _googleResponse.value = resource
                    }
                }
            } else {
                repository.signInWithGoogle(account).collect { resource ->
                    resource?.let {
                        val firebaseUser = it.data?.user
                        firebaseUser?.let { user ->
                            fisaaLogin(mkFisaaUser(user))
                            Transformations.map(_fisaaLoginResponse) { fisaaLoginResource ->
                                fisaaLoginResource?.let {
                                    if (fisaaLoginResource.status == Resource.Status.SUCCESS) {
                                        val fisaaUser = it.data?.data
                                        fisaaUser?.let {
                                            setId(fisaaUser._id)
                                        }

                                    }

                                }
                            }
                        }
                        _googleResponse.value = resource
                    }
                }
            }
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            repository.isUserExistsForEmail(account.email!!).collect { resource ->
                resource?.let {
                    val signInMethods = resource.data?.signInMethods ?: emptyList<String>()
                    val exists = signInMethods.isNotEmpty()
                    syncGoogleFirestore(account, exists)

                }


            }
        }


        //TODO replicate the same scenario of google here
        fun signInWithFacebook(token: AccessToken) {
            viewModelScope.launch {
                repository.signInWithFacebook(token).collect {
                    it?.let {
                        _facebookResponse.value = it
                    }
                }
            }
        }


    }


    fun signInWithFacebook(token: AccessToken) {
        viewModelScope.launch {
            repository.signInWithFacebook(token).collect {
                it?.let {
                    _facebookResponse.value = it
                }
            }
        }
    }

    fun saveToFirestore(fireUser: FireUser, scope: CoroutineScope) {
        scope.launch {
            repository.registerFirestore(fireUser)
                .collect { firestoreInstance ->
                    firestoreInstance?.let {
                        firestoreResponse.value = firestoreInstance
                    }
                }
        }
    }

    fun mkFirestoreUser(firebaseUser: FirebaseUser, fisaaUserId: String): FireUser {
        val fireUser = FireUser()
        val fullName = firebaseUser.displayName!!
        val firstName: String = fullName.split(" ").first()
        val lastName: String = fullName.split(" ").last()
        fireUser._id = fisaaUserId
        fireUser.email = firebaseUser.email!!
        fireUser.image = firebaseUser.photoUrl.toString()
        fireUser.firstName = firstName
        fireUser.lastName = lastName

        return fireUser
    }

    fun mkFisaaUser(firebaseUser: FirebaseUser): LoginQuery {
        val fullName = firebaseUser.displayName!!
        val firstName: String = fullName.split(" ").first()
        val lastName: String = fullName.split(" ").last()
        val loginQuery = LoginQuery(
            firebaseUser.email!!,
            social = true,
            image = firebaseUser.photoUrl!!.toString(),
            firstName = firstName,
            lastName = lastName
        )
        return loginQuery

    }


}