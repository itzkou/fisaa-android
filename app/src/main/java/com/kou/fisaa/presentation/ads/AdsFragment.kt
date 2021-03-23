package com.kou.fisaa.presentation.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.databinding.FragmentAdsBinding
import com.kou.fisaa.presentation.ads.adapter.AdsAdapter


class AdsFragment : Fragment(), AdsAdapter.Listener {

    private var _binding: FragmentAdsBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdsBinding.inflate(inflater, container, false)
        val view = binding.root

        val mAdsAdapter = AdsAdapter(this)
        binding.rvAds.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdsAdapter
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

        loadAds(mAdsAdapter)

        return view
    }

    override fun openUser(adId: String) {

    }

    private fun loadAds(adapter: AdsAdapter) {
        val ads = listOf(Advertisement("1"), Advertisement("2"))
        adapter.updateAds(ads)
    }
}