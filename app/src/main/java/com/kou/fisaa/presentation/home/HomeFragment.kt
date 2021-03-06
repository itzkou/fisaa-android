package com.kou.fisaa.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentHomeBinding
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter
import com.kou.fisaa.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)


    @Inject
    lateinit var upcomingAdapter: FlightsAdapter

    @Inject
    lateinit var topAdapter: FlightsAdapter

    @Inject
    lateinit var places: List<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpUi()
        searchFlights()
        getAllFlights()
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

    override fun onPause() {
        super.onPause()
        binding.homeSwipeToRefresh.isEnabled = false
    }

    override fun onResume() {
        super.onResume()
        binding.homeSwipeToRefresh.isEnabled = true

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    private fun searchFlights() {
        binding.edDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edDate)
        }
        binding.go.setOnClickListener {
            binding.go.hideKeyboard()
            val date = binding.edDate.text.toString()
            val destination = binding.edArrival.text.toString()
            val departure = binding.edDeparture.text.toString()
            val action =
                HomeFragmentDirections.actionHomeToFlightsFragment(
                    destination,
                    departure,
                    date,
                    source = "firstFilter"
                )
            findNavController().navigate(action)
        }

    }

    private fun setUpUi() {
        coordinateBtnAndInputs(binding.go, binding.edDeparture, binding.edArrival, binding.edDate)
        /*binding.navIt.setOnClickListener {
            requireActivity().findViewById<DrawerLayout>(R.id.drawer).openDrawer(0)
        }*/
        binding.rvUpcoming.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            upcomingAdapter.setOnFlightItemClickListener {

                requireActivity().toast(it)
            }
            adapter = upcomingAdapter
        }
        binding.rvTop.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topAdapter
        }


        val placeAdapter =
            ArrayAdapter(binding.edDeparture.context, R.layout.item_spinner_places, places)
        binding.edDeparture.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.radius_dropdown
            )
        )
        binding.edArrival.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.radius_dropdown
            )
        )
        binding.edDeparture.setAdapter(placeAdapter)
        binding.edArrival.setAdapter(placeAdapter)


    }

    private fun refresh() {
        binding.homeSwipeToRefresh.setOnRefreshListener {
            topAdapter.updateTopFlights(listOf())
            upcomingAdapter.updateUpcoming(listOf())
            viewModel.getUpcomingFlights()
            viewModel.getTopFlights()
        }

    }

    private fun getAllFlights() {
        binding.allFlights.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeToFlightsFragment(source = "all")
            findNavController().navigate(action)
        }
    }


}