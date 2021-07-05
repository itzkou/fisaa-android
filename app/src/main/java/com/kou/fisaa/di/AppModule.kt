package com.kou.fisaa.di

import android.content.Context
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kou.fisaa.R
import com.kou.fisaa.data.firestore.FirestoreAbstraction
import com.kou.fisaa.data.firestore.FirestoreRemote
import com.kou.fisaa.data.local.adLocalManager.AdLocalManager
import com.kou.fisaa.data.local.authLocalManager.AuthLocalManager
import com.kou.fisaa.data.local.flightLocalManager.FlightLocalManager
import com.kou.fisaa.data.local.roomManager.FisaaDatabase
import com.kou.fisaa.data.preferences.PrefsStore
import com.kou.fisaa.data.remote.FisaaApi
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.data.repository.FisaaRepository
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)   // all these dependencies inside AppModule will live as long as our application does
object AppModule {


    /**** Retrofit  ******/
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://fisaa.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    /**** Google Sign-In ******/

    @Provides

    fun provideGso(@ApplicationContext context: Context) =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    @Provides

    fun provideGoogleClient(
        @ApplicationContext context: Context,
        gso: GoogleSignInOptions
    ): GoogleSignInClient =
        GoogleSignIn.getClient(context, gso)

    /**** Facebook Sign-In ****/
    @Provides

    fun provideFacebookManager(): CallbackManager = CallbackManager.Factory.create()


    /**** Remote ******/
    @Provides
    fun provideFisaaApi(retrofit: Retrofit): FisaaApi = retrofit.create(FisaaApi::class.java)

    /**** local ******/

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        FisaaDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideAuthDao(db: FisaaDatabase) = db.authDao()

    @Singleton
    @Provides
    fun provideFlightDao(db: FisaaDatabase) = db.flightDao()

    @Singleton
    @Provides
    fun provideAdDao(db: FisaaDatabase) = db.adDao()

    /** DataStore **/
    @Provides
    @Singleton
    fun providePrefsStoreAbstraction(@ApplicationContext appContext: Context) =
        PrefsStore(appContext)


    /**** FireStore ******/
    @Provides
    fun provideFireBaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun provideFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun provideFirestoreRemote(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirestoreAbstraction = FirestoreRemote(firebaseAuth, firestore)

    /**** main Repo ******/
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO


    @Provides
    @Singleton
    fun provideFisaaRepositoryAbstraction(
        remote: FisaaRemote,
        authLocalManager: AuthLocalManager,
        flightLocalManager: FlightLocalManager,
        adLocalManager: AdLocalManager,
        firestore: FirestoreRemote,
        ioDispatcher: CoroutineDispatcher

    ): FisaaRepositoryAbstraction =
        FisaaRepository(
            remote,
            flightLocalManager,
            adLocalManager,
            firestore,
            ioDispatcher
        )


}