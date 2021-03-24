package com.kou.fisaa.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.data.entities.Flight
import com.kou.fisaa.databinding.FragmentHomeBinding
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter

class HomeFragment : Fragment(), FlightsAdapter.Listener  {


    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val upcomingAdapter = FlightsAdapter(this@HomeFragment)
        val topAdapter = FlightsAdapter(this@HomeFragment)

        binding.rvUpcoming.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
            isNestedScrollingEnabled=false
            setHasFixedSize(true)

        }
        binding.rvTop.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topAdapter
            isNestedScrollingEnabled=false
            setHasFixedSize(true)


        }

        loadUpcomingFlights(upcomingAdapter)
        loadTopFlights(topAdapter)


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    override fun openFlight(flightId: String) {
    }

    private fun loadUpcomingFlights(adapter: FlightsAdapter) {
        val flights = listOf(Flight("1",0), Flight("2",0), Flight("3",0))
        adapter.updateFlights(flights)
    }

    private fun loadTopFlights(adapter: FlightsAdapter) {
        val flights = listOf(Flight("1",1), Flight("2",1), Flight("3",1))
        adapter.updateFlights(flights)
    }


}