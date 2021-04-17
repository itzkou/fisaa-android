package com.kou.fisaa.presentation.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.FlightSearchQuery
import com.kou.fisaa.databinding.FragmentTripsBinding
import com.kou.fisaa.presentation.trips.adapter.TripAdapter
import com.kou.fisaa.presentation.trips.adapter.TripAdapterItemListener
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//TODO(two actions , one destination , eachaction with different params)
@AndroidEntryPoint
class TripsFragment : Fragment(), TripAdapterItemListener {

    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TripViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    val args: TripsFragmentArgs by navArgs()

    @Inject
    lateinit var tripsAdapter: TripAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        val view = binding.root

        setupUi()
        refresh()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tripsResponse.observe(viewLifecycleOwner, { resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { tripsResponse ->
                        if (tripsResponse.flights.isNotEmpty()) {
                            binding.shimmerTrips.stopShimmer()
                            binding.shimmerTrips.visibility = View.GONE
                            binding.swipe.isRefreshing = false
                            tripsAdapter.updateTrips(tripsResponse.flights)
                        }

                    }
                }

                Resource.Status.ERROR -> {
                    resource?.let {
                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                Resource.Status.LOADING -> {
                    resource?.let {
                        Toast.makeText(requireActivity(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
        viewModel.searchFlights(FlightSearchQuery(args.date, args.departure, args.destination))
    }

    private fun setupUi() {
        binding.rvTrips.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = tripsAdapter
            isNestedScrollingEnabled = false //TODO("Check this later")
        }
    }

    private fun refresh() {
        binding.swipe.setOnRefreshListener {
            tripsAdapter.updateTrips(listOf())
            viewModel.searchFlights(FlightSearchQuery(args.date, args.departure, args.destination))
        }
    }

    override fun openFlight(flightId: String) {
        TODO("Not yet implemented")
    }
}