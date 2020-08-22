package com.riluq.consumerapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riluq.consumerapp.data.source.local.entity.FavoriteEntity
import com.riluq.consumerapp.databinding.ItemFavoriteBinding

class FavoriteAdapter(private val onMenuClickListener: OnClickListener) :
    ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder.from(
            parent
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        if (favorite != null) {
            holder.bind(favorite)
            holder.binding.imgMenu.setOnClickListener {
                onMenuClickListener.onClick(favorite, it)
            }
        }
    }

    class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            favoriteEntity: FavoriteEntity
        ) {
            binding.favorite = favoriteEntity
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(
                    binding
                )
            }
        }

    }

    class OnClickListener(val clickListener: (favoriteEntity: FavoriteEntity, view: View) -> Unit) {
        fun onClick(favoriteEntity: FavoriteEntity, view: View) =
            clickListener(favoriteEntity, view)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<FavoriteEntity>() {

        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem == newItem
        }

    }

}