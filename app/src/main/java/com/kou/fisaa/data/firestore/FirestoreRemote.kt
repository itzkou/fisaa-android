package com.kou.fisaa.data.firestore

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRemote @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signInWithGoogle(acct: GoogleSignInAccount) = firebaseAuth.signInWithCredential(
        GoogleAuthProvider.getCredential(acct.idToken, null)
    ).await()

}