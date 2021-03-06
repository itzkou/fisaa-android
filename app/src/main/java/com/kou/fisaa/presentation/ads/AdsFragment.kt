package com.kou.fisaa.presentation.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.AdSearchQuery
import com.kou.fisaa.databinding.FragmentAdsBinding
import com.kou.fisaa.presentation.ads.adapter.AdsAdapter
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AdsFragment : Fragment() {

    private var _binding: FragmentAdsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: AdsViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val adsFragmentArgs: AdsFragmentArgs by navArgs()

    @Inject
    lateinit var adsAdapter: AdsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdsBinding.inflate(inflater, container, false)
        val view = binding.root

        setupUi()
        refresh()
        searchAndGetAds()



        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAds()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupUi() {
        binding.rvAds.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adsAdapter.setAdClickListener {
                requireActivity().toast(it)
            }
            adapter = adsAdapter

        }
        binding.search.setOnClickListener {
            val action = AdsFragmentDirections.actionAdsToSearchAdsFragment()
            findNavController().navigate(action)
        }

        adsAdapter.setEnableMyAds(false)
    }

    private fun loadAds() {
        viewmodel.adsResponse.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { adsResponse ->
                        if (adsResponse.ads.isNotEmpty()) {
                            binding.shimmerAds.stopShimmer()
                            binding.shimmerAds.visibility = View.GONE
                            binding.adsSwipeToRefresh.isRefreshing = false
                            adsAdapter.updateAds(adsResponse.ads)
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

    private fun refresh() {
        binding.adsSwipeToRefresh.setOnRefreshListener {
            adsAdapter.updateAds(listOf())
            viewmodel.getAds()

        }
    }

    private fun searchAndGetAds() {

        if (adsFragmentArgs.source == "all")
            viewmodel.getAds()
        else if (adsFragmentArgs.source == "search")
            viewmodel.searchAds(
                AdSearchQuery(
                    adsFragmentArgs.departureDate,
                    adsFragmentArgs.arivalDate,
                    adsFragmentArgs.departure,
                    adsFragmentArgs.destination
                )
            )

    }

}