package com.kou.fisaa.presentation.transactions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Message
import com.kou.fisaa.databinding.ChatFromBinding
import com.kou.fisaa.databinding.ChatToBinding

class ChatAdapter(private val fromId: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FROM = 1
        const val TO = 2

    }

    private var messages = arrayListOf<Message>()

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

                    if (message.text.isNotEmpty()) {
                        tvTo.visibility = View.VISIBLE
                        tvTo.text = message.text
                    } else
                        tvTo.visibility = View.GONE
                    if (message.image.isNotEmpty()) {
                        msgPhoto.visibility = View.VISIBLE
                        msgPhoto.load(message.image)
                    } else
                        msgPhoto.visibility = View.GONE
                    if (message.advertisement?.parcel != null) View.VISIBLE else View.GONE

                    message.advertisement?.let { adv ->
                        adv.parcel?.let { parcel ->
                            destination.text = adv.destination
                            departure.text = adv.departure
                            destination.text = parcel.description
                            parcelBonus.text =
                                holder.itemView.context.getString(R.string.currency, parcel.bonus)
                            parcelPhoto.load(parcel.photo)
                        }
                    }


                }
            }

            FROM -> {
                with((holder as FromViewHolder).binding) {

                    if (message.text.isNotEmpty()) {
                        tvFrom.visibility = View.VISIBLE
                        tvFrom.text = message.text
                    } else
                        tvFrom.visibility = View.GONE
                    if (message.image.isNotEmpty()) {
                        msgPhoto.visibility = View.VISIBLE
                        msgPhoto.load(message.image)
                    } else
                        msgPhoto.visibility = View.GONE
                    if (message.advertisement?.parcel != null) View.VISIBLE else View.GONE

                    message.advertisement?.let { adv ->
                        adv.parcel?.let { parcel ->
                            destination.text = adv.destination
                            departure.text = adv.departure
                            destination.text = parcel.description
                            parcelBonus.text =
                                holder.itemView.context.getString(R.string.currency, parcel.bonus)
                            parcelPhoto.load(parcel.photo)
                        }
                    }


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


    fun add(chatMsg: Message) {
        messages.add(chatMsg)
        notifyItemInserted(messages.size)
    }


}