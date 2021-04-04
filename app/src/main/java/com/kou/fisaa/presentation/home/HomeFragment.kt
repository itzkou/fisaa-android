package com.kou.fisaa.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Flight
import com.kou.fisaa.data.entities.FlightSearchQuery
import com.kou.fisaa.databinding.FragmentHomeBinding
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs

class HomeFragment : Fragment(), FlightsAdapter.Listener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val upcomingAdapter = FlightsAdapter(this@HomeFragment)
        val topAdapter = FlightsAdapter(this@HomeFragment)
        coordinateBtnAndInputs(binding.go, binding.edDeparture, binding.edArrival, binding.edDate)
        searchFlights()


        binding.rvUpcoming.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
            isNestedScrollingEnabled = false
            setHasFixedSize(true)

        }
        binding.rvTop.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topAdapter
            isNestedScrollingEnabled = false
            setHasFixedSize(true)


        }

        searchFlights()
        loadUpcomingFlights(upcomingAdapter)
        loadTopFlights(topAdapter)


        viewModel.flightSearchResponse.observe(requireActivity(), { resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { flightSearchResponse ->

                        Toast.makeText(
                            requireActivity(),
                            flightSearchResponse.flights[0].departure,
                            Toast.LENGTH_SHORT
                        ).show()

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


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun openFlight(flightId: String) {
    }

    private fun searchFlights() {
        binding.go.setOnClickListener {
            viewModel.searchFlights(
                FlightSearchQuery(
                    binding.edDate.text.toString(),
                    binding.edDeparture.text.toString(),
                    binding.edArrival.text.toString()
                )
            )
        }

    }

    private fun loadUpcomingFlights(adapter: FlightsAdapter) {
        val flights = listOf(Flight("1", 0), Flight("2", 0), Flight("3", 0))
        adapter.updateFlights(flights)
    }

    private fun loadTopFlights(adapter: FlightsAdapter) {
        val flights = listOf(Flight("1", 1), Flight("2", 1), Flight("3", 1))
        adapter.updateFlights(flights)
    }


}