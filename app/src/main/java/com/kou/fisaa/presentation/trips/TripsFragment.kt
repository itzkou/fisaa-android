package com.kou.fisaa.presentation.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.FlightSearchQuery
import com.kou.fisaa.databinding.FragmentFlightsBinding
import com.kou.fisaa.utils.Resource

class TripsFragment : Fragment() {

    private var _binding: FragmentFlightsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TripViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    val args: TripsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlightsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.flightSearchResponse.observe(viewLifecycleOwner, { resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { flightSearchResponse ->
                        Toast.makeText(requireActivity(), "meow", Toast.LENGTH_SHORT)
                            .show()

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


}