package com.kou.fisaa.presentation.transactions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.databinding.ItemUserBinding
import com.kou.fisaa.utils.SimpleCallback

class UsersAdapter constructor(private val listener: Listener) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var users = listOf<User>()

    interface Listener {
        fun talkToUser(userId: String)

    }

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
        val user = users[position]

        with(holder.binding) {
            tvUsername.text = user._id
        }


    }

    override fun getItemCount() = users.size


    fun updateMsgs(newUsers: List<User>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.users, newUsers) { it._id })
        this.users = newUsers
        diffResult.dispatchUpdatesTo(this)
    }


}