package com.kou.fisaa.presentation.trips.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Trip
import com.kou.fisaa.databinding.ItemTripBinding
import com.kou.fisaa.utils.SimpleCallback
import com.kou.fisaa.utils.setDate
import com.kou.fisaa.utils.stringToDate

//TODO clear a bug when user creates a new flight
class TripAdapter constructor(private val tripItemListener: TripAdapterItemListener) :
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
        if (trip.createdBy != null)
            with(holder.binding) {
                if (!trip.departureDate.isEmpty())
                    date.setDate(stringToDate(trip.departureDate))
                if (trip.createdBy.image.isNullOrEmpty())
                    picture.load(ContextCompat.getDrawable(picture.context, R.drawable.ic_face))
                else picture.load(trip.createdBy.image)
                name.text = holder.itemView.context.getString(
                    R.string.fullname,
                    trip.createdBy.firstName,
                    trip.createdBy.lastName
                )
                departure.text = trip.departure
                arrival.text = trip.destination
                chat.setOnClickListener {
                    tripItemListener.openChat(trip.createdBy._id)
                }
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