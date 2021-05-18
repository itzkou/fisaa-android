package com.kou.fisaa.data.firestore

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kou.fisaa.data.entities.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRemote @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirestoreAbstraction {
    val usersCollectionReference = firestore.collection("users")
    val chatsCollectionReference = firestore.collection("chat")


    override suspend fun signInWithGoogle(acct: GoogleSignInAccount): AuthResult =
        firebaseAuth.signInWithCredential(
            GoogleAuthProvider.getCredential(acct.idToken, null)
        ).await()

    override suspend fun signInWithFacebook(token: AccessToken): AuthResult =
        firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(token.token)).await()

    override suspend fun login(email: String, password: String): AuthResult =
        firebaseAuth.signInWithEmailAndPassword(email, password).await()


    override suspend fun register(email: String, password: String): AuthResult =
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    override suspend fun registerFirestore(user: User): DocumentReference {
        return usersCollectionReference.add(user).await()
    }


}