package com.kou.fisaa.presentation.transactions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.databinding.ItemUserBinding
import com.kou.fisaa.utils.SimpleCallback
import com.kou.fisaa.utils.setCaptionText
import com.kou.fisaa.utils.setDate
import java.util.*
import javax.inject.Inject

class TransactionAdapter @Inject constructor() :
    RecyclerView.Adapter<TransactionAdapter.UserViewHolder>() {

    private var messages = listOf<Message>()
    private var msgsItemListener: ((String) -> Unit)? = null


    class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val message = messages[position]
        with(holder.binding) {
            root.setOnClickListener {
                msgsItemListener?.let { callback ->
                    callback(message.fromId)
                }
            }
            if (message.senderPhoto.isEmpty())
                picture.load(ContextCompat.getDrawable(picture.context, R.drawable.ic_face))
            else
                picture.load(message.senderPhoto)

            date.setDate(Date(message.timeStamp * 1000))

            latestMsg.text = message.text

            caption.setCaptionText(message.senderName, "veut envoyer ")
        }


    }

    override fun getItemCount() = messages.size


    fun updateMsgs(newMsgs: List<Message>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.messages, newMsgs) { it.fromId })
        this.messages = newMsgs
        diffResult.dispatchUpdatesTo(this)
    }


    fun setOnUserClickListener(callback: ((String) -> Unit)) {
        this.msgsItemListener = callback
    }


}