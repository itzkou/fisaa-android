package com.kou.fisaa.presentation.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentTransactionBinding
import com.kou.fisaa.presentation.transactions.adapter.TransactionAdapter
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val transArgs: TransactionFragmentArgs by navArgs()

    @Inject
    lateinit var transactionAdapter: TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val view = binding.root
        listenTransactions(transArgs.fromId)
        setupUi()

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactions.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {
                        val users = resource.data
                        if (!users.isNullOrEmpty())
                            transactionAdapter.updateMsgs(users)

                    }
                }
                Resource.Status.ERROR -> resource?.let {
                    requireActivity().toast(resource.message.toString())
                }
                Resource.Status.LOADING -> requireActivity().toast("loading")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupUi() {

        binding.rvUsers.apply {
            transactionAdapter.setOnUserClickListener {
                requireActivity().toast(it)
            }
            adapter = transactionAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    private fun listenTransactions(fromId: String) {
        viewModel.listenTransactions(fromId)


    }


}
