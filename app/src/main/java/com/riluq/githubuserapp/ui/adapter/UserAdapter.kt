package com.riluq.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riluq.githubuserapp.data.source.remote.response.User
import com.riluq.githubuserapp.databinding.ItemUserBinding

class UserAdapter(private val clickListener: OnClickListener) :
    ListAdapter<User, UserAdapter.UserViewHolder>(
        Diffcallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val searchUser = getItem(position)
        holder.bind(searchUser)

        holder.itemView.setOnClickListener {
            clickListener.onClick(searchUser)
        }
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.searchUser = user
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(
                    binding
                )
            }
        }
    }

    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }

    private companion object Diffcallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

    }
}