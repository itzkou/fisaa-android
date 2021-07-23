package com.kou.fisaa.presentation.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentModifyAdsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyAdsFragment : Fragment() {
    private var _binding: FragmentModifyAdsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ModifyAdsViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentModifyAdsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}