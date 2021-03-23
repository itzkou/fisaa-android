package com.kou.fisaa.presentation.ads.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.databinding.ItemAdsBinding
import com.kou.fisaa.utils.SimpleCallback

class AdsAdapter(private val listener: Listener) : RecyclerView.Adapter<AdsAdapter.ViewHolder>() {

    interface Listener {
        fun openUser(adId: String)

    }

    inner class ViewHolder(val binding: ItemAdsBinding) : RecyclerView.ViewHolder(binding.root)

    private var ads = listOf<Advertisement>()


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


        }

    }

    override fun getItemCount() = ads.size

    fun updateAds(newAds: List<Advertisement>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.ads, newAds) { it.id }) //TODO check it
        this.ads = newAds
        diffResult.dispatchUpdatesTo(this)
    }


}