package com.kou.fisaa.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.data.entities.TopFLight
import com.kou.fisaa.databinding.ItemTopFlightsBinding
import com.kou.fisaa.utils.SimpleCallback

class TopFlightsAdapter(private val listener: Listener)
    : RecyclerView.Adapter<TopFlightsAdapter.ViewHolder>() {

    interface Listener {
        fun openTopFlight(flightId: String)

    }

   inner class ViewHolder(val binding: ItemTopFlightsBinding) : RecyclerView.ViewHolder(binding.root)

    private var flights = listOf<TopFLight>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTopFlightsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight = flights[position]


        with(holder.binding) {
            tvArrival.text= "test"
            tvDeparture.text="test"

        }

    }

    override fun getItemCount() = flights.size

    fun updateTopFlights(newTopFlights: List<TopFLight>) {
        val diffResult = DiffUtil.calculateDiff(SimpleCallback(this.flights, newTopFlights) { it.id }) //TODO check it
        this.flights = newTopFlights
        diffResult.dispatchUpdatesTo(this)
    }





}