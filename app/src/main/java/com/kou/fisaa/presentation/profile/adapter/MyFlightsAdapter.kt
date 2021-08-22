package com.kou.fisaa.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.data.entities.PublishedAdvert
import com.kou.fisaa.databinding.ItemMyTripsBinding
import com.kou.fisaa.utils.SimpleCallback

class MyFlightsAdapter : RecyclerView.Adapter<MyFlightsAdapter.ViewHolder>() {

    private var trips = listOf<PublishedAdvert>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MyFlightsAdapter.ViewHolder(
            ItemMyTripsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = trips[position]
        with(holder.binding) {

        }

    }


    override fun getItemCount(): Int {
        return trips.size
    }


    fun updateTrips(newTrips: List<PublishedAdvert>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.trips, newTrips) { it._id })
        this.trips = newTrips
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemMyTripsBinding) : RecyclerView.ViewHolder(binding.root)
}