package com.kou.fisaa.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Flight
import com.kou.fisaa.databinding.ItemTopFlightsBinding
import com.kou.fisaa.databinding.ItemUpcomingFlightsBinding
import com.kou.fisaa.utils.SimpleCallback
import com.kou.fisaa.utils.loadCircle
import javax.inject.Inject

//TODO search why viewtype is only usable when we have different items (objects) not layouts
class FlightsAdapter @Inject constructor(private val flightAdapterItemListener: FlightAdapterItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_UPCOMING = 0
        const val TYPE_TOP = 1
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
                    tvArrival.text = flight.departure
                    tvDeparture.text = flight.destination
                }
            }

            TYPE_UPCOMING -> {
                with((holder as UpcomingViewHolder).binding) {

                    name.text = holder.itemView.context.getString(
                        R.string.fullname,
                        flight.createdBy?.firstName,
                        flight.createdBy?.lastName
                    )
                    departure.text = flight.departure
                    arrival.text = flight.destination
                    picture.loadCircle(flight.createdBy?.image)


                }
            }
        }


    }

    override fun getItemCount() = flights.size

    override fun getItemViewType(position: Int): Int {
        return flights[position].viewType
    }

    fun updateUpcoming(newFlights: List<Flight>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.flights, newFlights) { it._id })
        this.flights = newFlights
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateTopFlights(newFlights: List<Flight>) {

        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.flights, newFlights) { it.count ?: 0 })
        this.flights = newFlights
        diffResult.dispatchUpdatesTo(this)
    }


}