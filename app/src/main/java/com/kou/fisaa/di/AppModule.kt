package com.kou.fisaa.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kou.fisaa.R
import com.kou.fisaa.data.local.FisaaDatabase
import com.kou.fisaa.data.local.FisaaDao
import com.kou.fisaa.data.remote.FisaaRemote
import com.kou.fisaa.data.remote.FisaaApi
import com.kou.fisaa.data.repository.FisaaRepository
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
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://fisaa.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    /**** Remote ******/

    @Provides
    fun provideFisaaApi(retrofit: Retrofit): FisaaApi = retrofit.create(FisaaApi::class.java)



    /**** local ******/

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = FisaaDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideFisaaDao(db: FisaaDatabase) = db.fisaaDao()


    /**** main Repo ******/
    @Singleton
    @Provides
    fun provideRepo(remote: FisaaRemote,
                    local: FisaaDao) =
        FisaaRepository(remote, local)
}