package com.kou.fisaa.data.firestore

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult

interface FirestoreAbstraction {

    suspend fun signInWithGoogle(acct: GoogleSignInAccount): AuthResult
    suspend fun signInWithFacebook(token: AccessToken): AuthResult
    suspend fun login(email: String, password: String): AuthResult  //TODO
    suspend fun register(email: String, password: String): AuthResult //TODO


}