package com.kou.fisaa.presentation.flights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentFlightsBinding
import com.kou.fisaa.presentation.home.HomeViewModel
import com.kou.fisaa.utils.Resource

class FlightsFragment : Fragment() {

    private var _binding: FragmentFlightsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlightsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.flightSearchResponse.observe(requireActivity(), { resource ->

            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { flightSearchResponse ->
                        if (flightSearchResponse.flights.isNotEmpty())
                            binding.textView9.text = flightSearchResponse.flights.toString()

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

        binding.imBack.setOnClickListener {
            findNavController().navigate(R.id.action_flightsFragment_to_home)
        }
        return view
    }


}