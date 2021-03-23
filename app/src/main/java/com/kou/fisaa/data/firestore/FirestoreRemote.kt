package com.kou.fisaa.data.firestore

import com.facebook.AccessToken
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRemote @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signInWithGoogle(acct: GoogleSignInAccount): AuthResult =
        firebaseAuth.signInWithCredential(
            GoogleAuthProvider.getCredential(acct.idToken, null)
        ).await()

    suspend fun signInWithFacebook(token: AccessToken): AuthResult =
        firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(token.token)).await()

    suspend fun login(email: String, password: String): AuthResult =
        firebaseAuth.signInWithEmailAndPassword(email, password).await()


    suspend fun register(email: String, password: String): AuthResult =
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()


}