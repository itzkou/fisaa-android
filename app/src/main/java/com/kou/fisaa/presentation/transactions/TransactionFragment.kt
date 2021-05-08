package com.kou.fisaa.presentation.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val transArgs: TransactionFragmentArgs by navArgs()

    private val viewModel: TransactionViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tx.text = transArgs.userId
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
