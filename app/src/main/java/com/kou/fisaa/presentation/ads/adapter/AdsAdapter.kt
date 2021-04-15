package com.kou.fisaa.presentation.ads.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.databinding.ItemAdsBinding
import com.kou.fisaa.utils.SimpleCallback


class AdsAdapter(private val listener: Listener) : RecyclerView.Adapter<AdsAdapter.ViewHolder>() {

    interface Listener {
        fun openUser(adId: String)

    }


    private var ads = listOf<Advertisement>()

    class ViewHolder(val binding: ItemAdsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ad = ads[position]


        with(holder.binding) {
            picture.load(ad.createdBy.image)
            name.text = holder.itemView.context.getString(
                R.string.fullname,
                ad.createdBy.firstName,
                ad.createdBy.lastName
            )
            departure.text = ad.departure
            arrival.text = ad.destination


            //date.setDate(Date(ad.departureDate as String))


        }

    }

    override fun getItemCount() = ads.size

    fun updateAds(newAds: List<Advertisement>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.ads, newAds) { it._id })
        this.ads = newAds
        diffResult.dispatchUpdatesTo(this)
    }


}