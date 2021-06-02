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

    @Provides
    fun providePlaces(): List<String> = listOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Antigua",
        "Argentina",
        "Armenia",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bhutan",
        "Bolivia",
        "Bosnia",
        "Botswana",
        "Brazil",
        "Brunei",
        "Bulgaria",
        "Burkina",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Cape Verde",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Comoros",
        "Congo",
        "Congo",
        "Costa Rica",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican",
        "East Timor",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial",
        "Eritrea",
        "Estonia",
        "Ethiopia",
        "Fiji",
        "Finland",
        "France",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Greece",
        "Grenada",
        "Guatemala",
        "Guinea",
        "Guinea-Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Israel",
        "Italy",
        "Ivory Coast",
        "Jamaica",
        "Japan",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Korea North",
        "Korea South",
        "Kosovo",
        "Kuwait",
        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Mauritania",
        "Mauritius",
        "Mexico",
        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Morocco",
        "Mozambique",
        "Myanmar",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Panama",
        "New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Poland",
        "Portugal",
        "Qatar",
        "Romania",
        "Russian Federation",
        "Rwanda",
        "St Kitts",
        "St Lucia",
        "Saint Vincent",
        "Samoa",
        "San Marino",
        "Sao Tome & Principe",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "South Sudan",
        "Spain",
        "Sri Lanka",
        "Sudan",
        "Suriname",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syria",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Togo",
        "Tonga",
        "Trinidad & Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Tuvalu",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "United States",
        "Uruguay",
        "Uzbekistan",
        "Vanuatu",
        "Vatican City",
        "Venezuela",
        "Vietnam",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )


}