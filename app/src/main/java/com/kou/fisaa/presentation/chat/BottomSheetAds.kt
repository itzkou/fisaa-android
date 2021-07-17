package com.kou.fisaa.presentation.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kou.fisaa.R
import com.kou.fisaa.databinding.BottomSheetAdsBinding
import com.kou.fisaa.presentation.ads.adapter.AdsAdapter
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetAds : BottomSheetDialogFragment() {
    private var _binding: BottomSheetAdsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ChatViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val chatArgs: ChatRoomFragmentArgs by navArgs()


    @Inject
    lateinit var adsAdapter: AdsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = BottomSheetAdsBinding.inflate(inflater, container, false)
        val view = binding.root

        setupUi()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.myAds.observe(viewLifecycleOwner, { resAds ->
            when (resAds.status) {
                Resource.Status.SUCCESS -> {
                    resAds.data?.ads?.let { ads ->
                        if (ads.isNotEmpty())
                            adsAdapter.updateAds(ads)
                    }

                }
                Resource.Status.LOADING -> Log.i("loadingBottomSheet", "onViewCreated: Loading")
                Resource.Status.ERROR -> Log.i("errorBomsheet", "onViewCreated: error ")
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUi() {
        adsAdapter.setEnableMyAds(true)

        binding.rvAds.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = adsAdapter
        }
        adsAdapter.setOnAdListener { id ->
            requireActivity().toast(id)


            findNavController().popBackStack()
        }
    }

}