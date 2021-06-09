package com.kou.fisaa.presentation.transactions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.databinding.ItemUserBinding
import com.kou.fisaa.utils.SimpleCallback
import javax.inject.Inject

class UsersAdapter @Inject constructor() :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var users = listOf<User>()
    private var usersItemClickListener: ((String) -> Unit)? = null


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
            root.setOnClickListener {
                usersItemClickListener?.let { callback ->
                    callback(user._id)
                }
            }
            if (user.image.isNullOrEmpty())
                picture.load(ContextCompat.getDrawable(picture.context, R.drawable.ic_face))
            else
                picture.load(user.image)

            latestMsg.text = "${user.firstName} wants to send xoxo"
        }


    }

    override fun getItemCount() = users.size


    fun updateMsgs(newUsers: List<User>) {
        val diffResult =
            DiffUtil.calculateDiff(SimpleCallback(this.users, newUsers) { it._id })
        this.users = newUsers
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnUserClickListener(callback: ((String) -> Unit)) {
        this.usersItemClickListener = callback
    }


}