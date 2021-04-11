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
import com.kou.fisaa.databinding.FragmentHomeBinding
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter
import com.kou.fisaa.utils.BuilderDatePicker
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs

class HomeFragment : Fragment(), FlightsAdapter.Listener {

    //TODO check requireActivity to viewlifecyclescope
    //TODO swipeToRefresh WIFI connectivity
    //TODO remove Loading States if not used
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)

    private val upcomingAdapter = FlightsAdapter(this@HomeFragment)
    private val topAdapter = FlightsAdapter(this@HomeFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpUi()
        searchFlights()
        viewModel.getUpcomingFlights()
        viewModel.getTopFlights()
        refresh()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.upcomingFlightsResponse.observe(viewLifecycleOwner,   // if we put requireActivity the code will crash when navigating to other fragments / viewLifeCycleOwner worked
            { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {

                        resource.data?.let { upcomingFlightsResponse ->


                            if (upcomingFlightsResponse.flights.isNotEmpty()) {
                                binding.shimmerUpcoming.stopShimmer()
                                binding.shimmerUpcoming.visibility = View.GONE
                                binding.homeSwipeToRefresh.isRefreshing = false

                                upcomingFlightsResponse.flights.forEach { flight ->
                                    flight.viewType = 0
                                }

                                upcomingAdapter.updateUpcoming(upcomingFlightsResponse.flights)

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

                        }
                    }
                }
            })
        viewModel.topFlightsResponse.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { topFlightsResponse ->
                        if (topFlightsResponse.flights.isNotEmpty()) {
                            binding.shimmerTop.stopShimmer()
                            binding.shimmerTop.visibility = View.GONE
                            topFlightsResponse.flights.forEach { flight ->
                                flight.viewType = 1
                            }
                            topAdapter.updateTopFlights(topFlightsResponse.flights)

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


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun openFlight(flightId: String) {
    }

    private fun searchFlights() {
        binding.edDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edDate)
        }
        binding.go.setOnClickListener {
            //pass data
        }

    }

    private fun setUpUi() {
        coordinateBtnAndInputs(binding.go, binding.edDeparture, binding.edArrival, binding.edDate)
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
    }

    private fun refresh() {
        binding.homeSwipeToRefresh.setOnRefreshListener {
            binding.shimmerUpcoming.visibility = View.VISIBLE
            binding.shimmerTop.visibility = View.VISIBLE
            binding.shimmerTop.startShimmer()
            binding.shimmerUpcoming.startShimmer()
            topAdapter.updateTopFlights(listOf())
            upcomingAdapter.updateUpcoming(listOf())
            viewModel.getUpcomingFlights()
            viewModel.getTopFlights()
        }

    }


}