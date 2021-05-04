package com.kou.fisaa.di

import android.content.Context
import android.widget.ArrayAdapter
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Material
import com.kou.fisaa.presentation.home.HomeFragment
import com.kou.fisaa.presentation.home.adapter.FlightAdapterItemListener
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter
import com.kou.fisaa.presentation.trips.TripsFragment
import com.kou.fisaa.presentation.trips.adapter.TripAdapter
import com.kou.fisaa.presentation.trips.adapter.TripAdapterItemListener
import com.kou.fisaa.utils.MaterialAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext


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
    fun provideMaterials(): ArrayList<Material> = arrayListOf(
        Material("clothing", R.drawable.box_blue),
        Material("electronic", R.drawable.box_blue),
        Material("books", R.drawable.box_blue),
        Material("documents", R.drawable.box_blue),
        Material("food", R.drawable.box_blue),
        Material("other", R.drawable.box_blue)
    )

    @Provides
    fun provideMaterialAdapter(
        @ApplicationContext context: Context,
        mData: ArrayList<Material>
    ): MaterialAdapter =
        MaterialAdapter(context, mData)

    @Provides
    fun provideWeights(): ArrayList<String> = arrayListOf("1K-2K", "3K-8K", "9K-20K", "20K+")

    @Provides
    fun provideWeightAdapter(
        @ApplicationContext context: Context,
        weighList: ArrayList<String>
    ) = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, weighList)


}