package com.kou.fisaa.data.firestore

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.kou.fisaa.data.entities.User

interface FirestoreAbstraction {

    /**** Firebase *****/
    suspend fun signInWithGoogle(acct: GoogleSignInAccount): AuthResult
    suspend fun signInWithFacebook(token: AccessToken): AuthResult
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult

    /*** Firestore ***/
    suspend fun registerFirestore(user: User): DocumentReference


}