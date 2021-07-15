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
import com.kou.fisaa.data.entities.Message
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
    private val latestMessagesMap = LinkedHashMap<String, Message>()


    @Inject
    lateinit var transactionAdapter: TransactionAdapter


    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val view = binding.root


        setupUi()


        return view

    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listenTransactions()
        viewModel.transaction.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let { resMsg ->
                        val msg = resMsg.data
                        val msgs = arrayListOf<Message>()
                        if (msg != null) {
                            latestMessagesMap[msg.fromId] = msg  // Populating the map
                            transactionAdapter.updateMsgs(latestMessagesMap.values.toList())
                        }

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


}
