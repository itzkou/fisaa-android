package com.kou.fisaa.presentation.search.flights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kou.fisaa.databinding.FragmentSearchFligthsBinding
import com.kou.fisaa.utils.BuilderDatePicker
import com.kou.fisaa.utils.coordinateBtnAndInputs

class SearchFlightsFragment : Fragment() {
    private var _binding: FragmentSearchFligthsBinding? = null
    private val binding get() = _binding!!

    lateinit var places: List<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchFligthsBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpUi()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSearchFilter.setOnClickListener {

        val arrival = binding.arrivalSearch.text.toString()
            val departure = binding.departureSearch.text.toString()
            val depDate = binding.edDepDate.text.toString()
            val arrDate = binding.edArrivalDate.text.toString()

            val action =
                SearchFlightsFragmentDirections.actionSearchFlightsFragmentToFlightsFragment4(
                    arrival,
                    departure,
                    depDate,
                    arrDate, source = "secondFilter"
                )
            findNavController().navigate(action)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpUi() {
        coordinateBtnAndInputs(
            binding.btnSearchFilter,
            binding.edArrivalDate,
            binding.edDepDate,
            binding.departureSearch,
            binding.arrivalSearch
        )
        binding.edDepDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edDepDate)
        }
        binding.edArrivalDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edArrivalDate)
        }
        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}