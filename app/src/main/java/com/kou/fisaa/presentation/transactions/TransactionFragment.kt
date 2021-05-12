package com.kou.fisaa.presentation.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.databinding.FragmentTransactionBinding
import com.kou.fisaa.presentation.transactions.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val transArgs: TransactionFragmentArgs by navArgs()
    private val chatAdapter = ChatAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val view = binding.root
        sendMsg()
        setupUi()

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupUi() {
        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun sendMsg() {
        val text = binding.edSendMsg.toString()
        val msgs = arrayListOf(
            Message("1", text, Firebase.auth.uid!!, "xx", 1620838815),
            Message("2", "  Hi kou", "xx", Firebase.auth.uid!!, 1620838815)
        )

        chatAdapter.updateMsgs(msgs)


/*
        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<UserFire>(USER_KEY)
        val toId = user.uid

        if (fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = Message(reference.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                edMsg.text.clear()
                rvChat.scrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)*/

    }
}
