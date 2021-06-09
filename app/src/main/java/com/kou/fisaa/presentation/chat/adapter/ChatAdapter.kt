package com.kou.fisaa.presentation.transactions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.databinding.ChatFromBinding
import com.kou.fisaa.databinding.ChatToBinding
import com.kou.fisaa.utils.SimpleCallback

class ChatAdapter(private val fromId: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FROM = 1
        const val TO = 2
    }

    private var messages = listOf<Message>()

    class FromViewHolder(val binding: ChatFromBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ToViewHolder(val binding: ChatToBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TO) {
            ToViewHolder(
                ChatToBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else FromViewHolder(
            ChatFromBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder.itemViewType) {
            TO -> {
                with((holder as ToViewHolder).binding) {
                    tvTo.text = message.text

                }
            }

            FROM -> {
                with((holder as FromViewHolder).binding) {
                    tvFrom.text = message.text

                }
            }
        }


    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.fromId == fromId)
            FROM
        else
            TO
    }

    fun updateMsgs(newMsgs: List<Message>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.messages, newMsgs) { it.timeStamp })
        this.messages = newMsgs
        diffResult.dispatchUpdatesTo(this)
    }


}