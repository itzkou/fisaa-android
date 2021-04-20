package com.kou.fisaa.di

import com.appexecutors.picker.utils.PickerOptions
import com.kou.fisaa.presentation.home.HomeFragment
import com.kou.fisaa.presentation.home.adapter.FlightAdapterItemListener
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter
import com.kou.fisaa.presentation.trips.TripsFragment
import com.kou.fisaa.presentation.trips.adapter.TripAdapter
import com.kou.fisaa.presentation.trips.adapter.TripAdapterItemListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
object AppUtilsModule {

    @Provides        //adding singleton = crash
    fun provideFlightAdapterItemListener(): FlightAdapterItemListener {
        return HomeFragment()
    }

    @Provides
    fun provideFlightsAdapter(flightAdapterItemListener: FlightAdapterItemListener): FlightsAdapter {
        return FlightsAdapter(flightAdapterItemListener)
    }

    @Provides        //adding singleton = crash
    fun provideTripsAdapterItemListener(): TripAdapterItemListener {
        return TripsFragment()  //always refrence the interface (listener) in fragment
    }

    @Provides
    fun provideTripsAdapter(tripAdapterItemListener: TripAdapterItemListener): TripAdapter {
        return TripAdapter(tripAdapterItemListener)
    }

    @Provides
    fun providePickerOptions(): PickerOptions = PickerOptions.init()


}