package com.kou.fisaa.presentation.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentModifyAdsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyAdsFragment : Fragment() {
    private var _binding: FragmentModifyAdsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ModifyAdsViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val modifyAdsArgs: ModifyAdsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentModifyAdsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUi()
        return view

    }

    private fun setupUi() {
        binding.edDate.setText(modifyAdsArgs.depDate)
        binding.departure.setText(modifyAdsArgs.departure)
        binding.destination.setText(modifyAdsArgs.destination)
        binding.imageToUpload.load(modifyAdsArgs.photo)
        binding.description.setText(modifyAdsArgs.description)
        binding.txBonus.setText(modifyAdsArgs.bonus.toString())
        binding.back.setOnClickListener {
            val action =
                ModifyAdsFragmentDirections.actionModifyAdsFragmentToChatRoomFragment(modifyAdsArgs.toId)
            findNavController().navigate(action)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}