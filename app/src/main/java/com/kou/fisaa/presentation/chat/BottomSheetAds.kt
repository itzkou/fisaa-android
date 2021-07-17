package com.kou.fisaa.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kou.fisaa.databinding.BottomSheetAdsBinding
import com.kou.fisaa.presentation.ads.adapter.AdsAdapter
import com.kou.fisaa.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetAds : BottomSheetDialogFragment() {
    private var _binding: BottomSheetAdsBinding? = null
    private val binding get() = _binding!!

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUi() {
        binding.rvAds.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adsAdapter.setOnAdListener {
                requireActivity().toast(it)
            }
            adapter = adsAdapter

        }
    }

}