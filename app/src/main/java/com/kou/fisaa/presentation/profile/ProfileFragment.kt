package com.kou.fisaa.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kou.fisaa.databinding.FragmentProfileBinding
import com.kou.fisaa.presentation.profile.adapter.PagerAdapter
import com.kou.fisaa.presentation.profile.fragments.MyFlightsFragment


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: PagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentList = arrayListOf<Fragment>(
            MyFlightsFragment(),
            MyFlightsFragment(),
            MyFlightsFragment()
        )

        mAdapter = PagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.pager.adapter = mAdapter

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        super.onDestroyView()
        _binding = null
    }
}