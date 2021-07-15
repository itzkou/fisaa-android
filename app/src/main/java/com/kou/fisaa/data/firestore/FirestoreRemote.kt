package com.kou.fisaa.data.firestore

import android.net.Uri
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.kou.fisaa.data.entities.FireUser
import com.kou.fisaa.data.entities.Message
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRemote @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore

) : FirestoreAbstraction {
    val storage = Firebase.storage.reference
    private val usersCollectionReference = firestore.collection("users")
    private val chatsCollectionReference = firestore.collection("chat")


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

    override suspend fun isUserExistsForEmail(email: String): SignInMethodQueryResult =
        firebaseAuth.fetchSignInMethodsForEmail(email).await()


    override suspend fun registerFirestore(user: FireUser): DocumentReference {
        return usersCollectionReference.add(user).await()
    }

    override suspend fun getUsers(): QuerySnapshot {
        return usersCollectionReference.get().await()
    }

    override suspend fun sendMsg(msg: Message): DocumentReference {
        return chatsCollectionReference.add(msg).await()
    }


    override suspend fun listenMsgs(fromId: String, toId: String): Query {
        return chatsCollectionReference.whereEqualTo("fromId", fromId).whereEqualTo("toId", toId)
            .orderBy("timeStamp")

    }

    override suspend fun listenTransactions(toId: String): Query {
        return chatsCollectionReference.whereEqualTo("toId", toId)
            .orderBy("timeStamp").limitToLast(1)
    }


    override suspend fun uploadParcelImage(imageUri: Uri): UploadTask.TaskSnapshot {
        return storage.child("parcels").child(imageUri.lastPathSegment!!).putFile(imageUri).await()
    }


}