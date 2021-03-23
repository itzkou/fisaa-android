package com.kou.fisaa.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.data.entities.UpcomingFlight
import com.kou.fisaa.databinding.ItemUpcomingFlightsBinding
import com.kou.fisaa.utils.SimpleCallback

class UpcomingFlightsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<UpcomingFlightsAdapter.ViewHolder>() {

    interface Listener {
        fun openUpcomingFlight(flightId: String)

    }

    inner class ViewHolder(val binding: ItemUpcomingFlightsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var flights = listOf<UpcomingFlight>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUpcomingFlightsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight = flights[position]


        with(holder.binding) {
            name.text = "test"
            departure.text = "test"
            arrival.text = "test"

        }

    }

    override fun getItemCount() = flights.size

    fun updateUpcomingFlights(newUpcomingFlights: List<UpcomingFlight>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.flights, newUpcomingFlights) { it.id })
        this.flights = newUpcomingFlights
        diffResult.dispatchUpdatesTo(this)
    }


}