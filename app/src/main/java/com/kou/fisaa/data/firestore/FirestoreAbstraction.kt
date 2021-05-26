package com.kou.fisaa.data.firestore

import android.net.Uri
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.data.entities.User

interface FirestoreAbstraction {

    /**** Firebase *****/
    suspend fun signInWithGoogle(acct: GoogleSignInAccount): AuthResult
    suspend fun signInWithFacebook(token: AccessToken): AuthResult
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult

    /*** Firestore ***/
    suspend fun registerFirestore(user: User): DocumentReference
    suspend fun getUsers(): QuerySnapshot
    suspend fun sendMsg(msg: Message): DocumentReference
    suspend fun listenMsgs(fromId: String, toId: String): Query

    /*** Storage ***/
    suspend fun uploadParcelImage(imageUri: Uri): UploadTask.TaskSnapshot

}