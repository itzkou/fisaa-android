package com.kou.fisaa.di

import android.content.Context
import android.widget.ArrayAdapter
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Material
import com.kou.fisaa.presentation.ads.AdsFragment
import com.kou.fisaa.presentation.ads.adapter.AdAdapterListener
import com.kou.fisaa.presentation.ads.adapter.AdsAdapter
import com.kou.fisaa.utils.MaterialAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(FragmentComponent::class)
object AppUtilsModule {


    @Provides
    fun provideAdsAdapterItemListener(): AdAdapterListener {
    return AdsFragment()   // this genrates an erro with navcomponentt because it creates a new instance of a frgament
}

    @Provides
    fun provideAdsAdapter(adAdapterListener: AdAdapterListener): AdsAdapter {
        return AdsAdapter(adAdapterListener)
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