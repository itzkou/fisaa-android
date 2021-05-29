package com.kou.fisaa.presentation.ads.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.databinding.ItemAdsBinding
import com.kou.fisaa.utils.SimpleCallback
import com.kou.fisaa.utils.setDate
import com.kou.fisaa.utils.stringToDate
import javax.inject.Inject


class AdsAdapter @Inject constructor(private val adapterListener: AdAdapterListener) :
    RecyclerView.Adapter<AdsAdapter.ViewHolder>() {

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
            if (ad.createdBy.image.isNullOrEmpty())
                picture.load(ContextCompat.getDrawable(picture.context, R.drawable.ic_face))
            else
                picture.load(ad.createdBy.image)
            name.text = holder.itemView.context.getString(
                R.string.fullname,
                ad.createdBy.firstName,
                ad.createdBy.lastName
            )
            departure.text = ad.departure
            arrival.text = ad.destination
            when (ad.type) {
                "purchase" -> type.text = holder.itemView.context.getString(R.string.purchase)
                "travel" -> type.text = holder.itemView.context.getString(R.string.travel)
                "transport" -> type.text = holder.itemView.context.getString(R.string.transport)
            }
            if (ad.parcel != null) {
                dimension.text = ad.parcel.dimension
                weight.text = ad.parcel.weight
            }
            if (ad.departureDate != null)
                date.setDate(stringToDate(ad.departureDate))


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