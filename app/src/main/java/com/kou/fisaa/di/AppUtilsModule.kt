package com.kou.fisaa.di

import com.kou.fisaa.presentation.home.HomeFragment
import com.kou.fisaa.presentation.home.adapter.FlightAdapterItemListener
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton


@Module
@InstallIn(FragmentComponent::class)
object AppUtilsModule {

    @Singleton
    @Provides
    fun provideFlightAdapterItemListener(): FlightAdapterItemListener {
        return HomeFragment()
    }

    @Singleton
    @Provides
    fun provideFlightsAdapter(flightAdapterItemListener: FlightAdapterItemListener): FlightsAdapter {
        return FlightsAdapter(flightAdapterItemListener)
    }

}