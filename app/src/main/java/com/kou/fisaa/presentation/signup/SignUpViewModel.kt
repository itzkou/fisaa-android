package com.kou.fisaa.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.kou.fisaa.data.entities.FireUser
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.createPartFromString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: FisaaRepositoryAbstraction,
    private val prefsStore: PrefsStore,

) : ViewModel() {

    private val _signUpResponse = MutableLiveData<Resource<User>>()
    val signupResponse = _signUpResponse
    private val _fireSignUpResponse = MutableLiveData<Resource<AuthResult>>()
    val fireSignUpResponse = _fireSignUpResponse
    private val _firestoreSignUpResponse = MutableLiveData<Resource<DocumentReference>>()
    val firestoreSignUpResponse = _firestoreSignUpResponse


    fun setId(id: String) {
        viewModelScope.launch {
            prefsStore.setId(id)
        }
    }

    fun setFireToken(id: String?) {
        viewModelScope.launch {
            id?.let {
                prefsStore.setFireToken(id)
            }

        }
    }

    fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        birthdate: String?,
        cin: String?,
        description: String?,
        phoneNumber: String?,
        adress: String?,
        city: String?,
        country: String?,
        zipcode: String?
    ) {

        val map: HashMap<String, RequestBody> = HashMap()

        map["email"] = createPartFromString(email)
        map["password"] = createPartFromString(password)
        map["firstName"] = createPartFromString(firstName)
        map["lastName"] = createPartFromString(lastName)
        map["dateOfBirth"] = createPartFromString(birthdate ?: "")
        map["cin"] = createPartFromString(cin ?: "0")
        map["description"] = createPartFromString(description ?: "")
        map["phoneNumber"] = createPartFromString(phoneNumber ?: "0")
        map["adress"] = createPartFromString(adress ?: "")
        map["city"] = createPartFromString(city ?: "")
        map["country"] = createPartFromString(country ?: "")
        map["zipCode"] = createPartFromString(zipcode ?: "0")

        viewModelScope.launch {
            repository.signUp(map).collect {
                it?.let {
                    _signUpResponse.value = it
                }
            }
        }
    }

    fun signUpFirebase(email: String, password: String) {
        viewModelScope.launch {
            repository.register(email, password).collect {
                it?.let {
                    _fireSignUpResponse.value = it
                }
            }
        }
    }

    fun signUpFirestore(user: FireUser) {
        viewModelScope.launch {
            repository.registerFirestore(user).collect {
                it?.let {
                    firestoreSignUpResponse.value = it
                }
            }
        }
    }
}
