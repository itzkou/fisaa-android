package com.kou.fisaa.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.databinding.FragmentChatRoomBinding
import com.kou.fisaa.presentation.transactions.adapter.ChatAdapter
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.loadAvatar
import com.kou.fisaa.utils.toast
import kotlinx.coroutines.launch


class ChatRoomFragment : Fragment() {
    private var _binding: FragmentChatRoomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val chatArgs: ChatRoomFragmentArgs by navArgs()
    private lateinit var mAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        val view = binding.root

        validateSession()
        getOtherUser()
        listenMsgs()




        return view
    }

    private fun validateSession() {
        viewModel.userId.observe(viewLifecycleOwner, { id ->
            if (id != null) {
                setupUi(id)
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.user.observe(viewLifecycleOwner, { resUser ->
            when (resUser.status) {
                Resource.Status.SUCCESS -> {
                    resUser.data?.let { from ->
                        sendMsg(from._id, from.firstName, from.image ?: "")

                    }
                }

                Resource.Status.LOADING -> {
                    requireActivity().toast("Loading")
                }

                Resource.Status.ERROR -> {
                    requireActivity().toast(resUser.message.toString())
                }
            }
        })
        viewModel.other.observe(viewLifecycleOwner, { resUser ->
            when (resUser.status) {
                Resource.Status.SUCCESS -> {
                    resUser.data?.let { other ->
                        binding.edChat.hint = "Répondez à ${other.firstName}"
                        binding.otherAvatar.loadAvatar(other.image)
                        binding.otherAvatar.loadAvatar(other.image)
                        binding.otherUsername.text = requireActivity().getString(
                            R.string.fullname,
                            other.firstName,
                            other.lastName
                        )
                    }
                }

                Resource.Status.LOADING -> {
                    requireActivity().toast("Loading")
                }

                Resource.Status.ERROR -> {
                    requireActivity().toast(resUser.message.toString())
                }
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.msg.observe(viewLifecycleOwner, { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {

                        resource?.let { msgsResource ->

                            msgsResource.data?.let { msg ->
                                mAdapter.add(msg)
                            }

                            binding.rvChats.scrollToPosition(mAdapter.itemCount - 1)

                        }
                    }
                    Resource.Status.ERROR -> {
                        requireActivity().toast(resource.message.toString())
                    }

                    Resource.Status.LOADING -> requireActivity().toast("loading")
                }
            })
        }
        viewModel.hasBeenSent.observe(viewLifecycleOwner,
            { hasBeenSent ->
                when (hasBeenSent.status) {
                    Resource.Status.SUCCESS -> {
                        requireActivity().toast("Sent")
                        binding.edChat.text.clear()

                    }

                    Resource.Status.ERROR -> hasBeenSent?.let {
                        requireActivity().toast(hasBeenSent.message.toString())
                    }

                    Resource.Status.LOADING -> hasBeenSent?.let { requireActivity().toast("loading") }

                }

            })
    }


    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }


    private fun setupUi(fromId: String) {
        mAdapter = ChatAdapter(fromId)
        binding.rvChats.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
    }

    private fun listenMsgs() {
        /** if you call listenMsgs using viewModelScope and When you come back from Fragment X to ChatRoomFragment, then ChatRoomFragment gets reattached. As a result fragment's onViewCreated gets called second time and you observe the same instance of Flow second time.
         *  Other words, now you have one Flow with many observers,
         *  and when the flow emits data, then many of them are called.which leads to duplicate msgs.The problem here is that when you dettach the fragment from the acitivity, both fragment and its viewmodel are not destroyed. When you come back, you add a new observer
         *  to the livedata when the old observer is still there in the same fragment
         **/
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listenMsgs(chatArgs.toId)

        }
    }

    private fun getOtherUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getOtherUser(chatArgs.toId)

        }
    }

    private fun sendMsg(
        fromId: String,
        senderName: String,
        senderPhoto: String,
    ) {

        binding.btnSend.setOnClickListener {
            val content = binding.edChat.text.toString()
            val chatMessage =
                Message(
                    fromId,
                    chatArgs.toId,
                    content,
                    senderPhoto,
                    senderName
                )

            viewModel.sendMsg(chatMessage)
        }

    }


}