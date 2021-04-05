package com.kou.fisaa.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kou.fisaa.R
import com.kou.fisaa.data.firestore.FirestoreRemote
import com.kou.fisaa.data.local.authLocalManager.AuthLocalManager
import com.kou.fisaa.data.local.roomManager.FisaaDatabase
import com.kou.fisaa.data.remote.FisaaApi
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.data.repository.FisaaRepository
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)   // all these dependencies inside AppModule will live as long as our application does
object AppModule {

    /**** Glide  ******/
    @Singleton // This specific glide instance has only one instance inside our app
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )

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
    @Singleton
    fun provideGso(@ApplicationContext context: Context) =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    @Provides
    @Singleton
    fun provideGoogleClient(
        @ApplicationContext context: Context,
        gso: GoogleSignInOptions
    ): GoogleSignInClient =
        GoogleSignIn.getClient(context, gso)

    /**** Facebook Sign-In ****/
    @Provides
    @Singleton
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
    fun provideFisaaDao(db: FisaaDatabase) = db.fisaaDao()


    /**** FireStore ******/
    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirestore() = Firebase.firestore

    /**** main Repo ******/
    /* @Singleton
     @Provides
     fun provideFisaaRepository(
         remote: FisaaRemote,
         local: AuthLocalManager, firestore: FirestoreRemote
     ) = FisaaRepository(remote, local, firestore)*/

    @Provides
    @Singleton
    fun provideFisaaRepositoryAbstraction(
        remote: FisaaRemote,
        local: AuthLocalManager,
        firestore: FirestoreRemote
    ): FisaaRepositoryAbstraction = FisaaRepository(remote, local, firestore)


}