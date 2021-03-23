package com.kou.fisaa.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.data.entities.TopFLight
import com.kou.fisaa.data.entities.UpcomingFlight
import com.kou.fisaa.databinding.FragmentHomeBinding
import com.kou.fisaa.presentation.home.adapter.TopFlightsAdapter
import com.kou.fisaa.presentation.home.adapter.UpcomingFlightsAdapter

class HomeFragment : Fragment(), UpcomingFlightsAdapter.Listener, TopFlightsAdapter.Listener {


    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val mUpcomingAdapter = UpcomingFlightsAdapter(this@HomeFragment)
        val mTopAdapter = TopFlightsAdapter(this@HomeFragment)
        binding.rvUpcoming.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mUpcomingAdapter
            isNestedScrollingEnabled=false
            setHasFixedSize(true)

        }
        binding.rvTop.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mTopAdapter
            isNestedScrollingEnabled=false
            setHasFixedSize(true)


        }

        loadUpcomingFligths(mUpcomingAdapter)
        loadTopFligths(mTopAdapter)

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun openTopFlight(flightId: String) {
    }

    override fun openUpcomingFlight(flightId: String) {
    }

    private fun loadUpcomingFligths(adapter: UpcomingFlightsAdapter) {
        val flights = listOf(UpcomingFlight("1"), UpcomingFlight("2"), UpcomingFlight("3"))
        adapter.updateUpcomingFlights(flights)
    }

    private fun loadTopFligths(adapter: TopFlightsAdapter) {
        val flights = listOf(TopFLight("1"), TopFLight("2"), TopFLight("3"))
        adapter.updateTopFlights(flights)
    }
}