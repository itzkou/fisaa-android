package com.kou.fisaa.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.data.entities.Flight
import com.kou.fisaa.databinding.ItemTopFlightsBinding
import com.kou.fisaa.databinding.ItemUpcomingFlightsBinding
import com.kou.fisaa.utils.SimpleCallback

class FlightsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener {
        fun openFlight(flightId: String)
    }

    companion object {
        const val TYPE_UPCOMING = 0
        const val TYPE_TOP =1
    }

    private var flights = listOf<Flight>()

    inner class UpcomingViewHolder(val binding: ItemUpcomingFlightsBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class TopViewHolder(val binding: ItemTopFlightsBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_TOP) {
            TopViewHolder(
                ItemTopFlightsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else UpcomingViewHolder(
            ItemUpcomingFlightsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val flight = flights[position]

        when (flight.viewType) {
            TYPE_TOP -> {
                with((holder as TopViewHolder).binding) {
                    tvArrival.text = "test"
                    tvDeparture.text = "test"
                }
            }

            TYPE_UPCOMING -> {
                with((holder as UpcomingViewHolder).binding) {
                    name.text = "test"
                    departure.text = "test"
                    arrival.text = "test"

                }
            }
        }


    }

    override fun getItemCount() = flights.size
    override fun getItemViewType(position: Int): Int {
        return flights[position].viewType
    }

    fun updateFlights(newFlights: List<Flight>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.flights, newFlights) { it.id })
        this.flights = newFlights
        diffResult.dispatchUpdatesTo(this)
    }


}