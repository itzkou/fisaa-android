package com.kou.fisaa.presentation.trips.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Trip
import com.kou.fisaa.databinding.ItemTripBinding
import com.kou.fisaa.utils.SimpleCallback
import com.kou.fisaa.utils.loadCircle
import com.kou.fisaa.utils.setDate
import com.kou.fisaa.utils.stringToDate
import javax.inject.Inject

//TODO( make user Image nullable or give him a placeholder by default )
class TripAdapter @Inject constructor(private val tripItemListener: TripAdapterItemListener) :
    RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    private var trips = listOf<Trip>()


    class ViewHolder(val binding: ItemTripBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTripBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = trips[position]

        with(holder.binding) {
            date.setDate(stringToDate(trip.departureDate))
            picture.loadCircle(trip.createdBy.image)
            name.text = holder.itemView.context.getString(
                R.string.fullname,
                trip.createdBy.firstName,
                trip.createdBy.lastName
            )
            departure.text = trip.departure
            arrival.text = trip.destination
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }


    fun updateTrips(newTrips: List<Trip>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.trips, newTrips) { it._id })
        this.trips = newTrips
        diffResult.dispatchUpdatesTo(this)
    }

}