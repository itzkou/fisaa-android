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
import com.kou.fisaa.presentation.home.adapter.FlightAdapterItemListener
import com.kou.fisaa.presentation.home.adapter.FlightsAdapter
import com.kou.fisaa.utils.BuilderDatePicker
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs
import com.kou.fisaa.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), FlightAdapterItemListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)


    @Inject
    lateinit var upcomingAdapter: FlightsAdapter

    @Inject
    lateinit var topAdapter: FlightsAdapter


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


    override fun openFlight(flightId: String) {
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
            adapter = upcomingAdapter
        }
        binding.rvTop.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topAdapter
        }

        val places = listOf(
            "Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua",
            "Argentina",
            "Armenia",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bhutan",
            "Bolivia",
            "Bosnia",
            "Botswana",
            "Brazil",
            "Brunei",
            "Bulgaria",
            "Burkina",
            "Burundi",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Cape Verde",
            "Chad",
            "Chile",
            "China",
            "Colombia",
            "Comoros",
            "Congo",
            "Congo",
            "Costa Rica",
            "Croatia",
            "Cuba",
            "Cyprus",
            "Czech",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican",
            "East Timor",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial",
            "Eritrea",
            "Estonia",
            "Ethiopia",
            "Fiji",
            "Finland",
            "France",
            "Gabon",
            "Gambia",
            "Georgia",
            "Germany",
            "Ghana",
            "Greece",
            "Grenada",
            "Guatemala",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Honduras",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Ireland",
            "Israel",
            "Italy",
            "Ivory Coast",
            "Jamaica",
            "Japan",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Korea North",
            "Korea South",
            "Kosovo",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macedonia",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands",
            "Mauritania",
            "Mauritius",
            "Mexico",
            "Micronesia",
            "Moldova",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Morocco",
            "Mozambique",
            "Myanmar",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Panama",
            "New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Poland",
            "Portugal",
            "Qatar",
            "Romania",
            "Russian Federation",
            "Rwanda",
            "St Kitts",
            "St Lucia",
            "Saint Vincent",
            "Samoa",
            "San Marino",
            "Sao Tome & Principe",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "South Africa",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Swaziland",
            "Sweden",
            "Switzerland",
            "Syria",
            "Taiwan",
            "Tajikistan",
            "Tanzania",
            "Thailand",
            "Togo",
            "Tonga",
            "Trinidad & Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom",
            "United States",
            "Uruguay",
            "Uzbekistan",
            "Vanuatu",
            "Vatican City",
            "Venezuela",
            "Vietnam",
            "Yemen",
            "Zambia",
            "Zimbabwe"
        )
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