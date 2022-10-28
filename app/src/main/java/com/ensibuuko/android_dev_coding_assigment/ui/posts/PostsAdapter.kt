package com.ensibuuko.android_dev_coding_assigment.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.databinding.ItemPostsBinding

class PostsAdapter :
    ListAdapter<Posts, PostsAdapter.PostsViewHolder>(CallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class PostsViewHolder(private val binding: ItemPostsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(posts: Posts) {
            binding.apply {
                tvPostTitle.text = posts.title
                tvPostBody.text = posts.body
            }
        }
    }

    class CallBack : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean =
            oldItem == newItem
    }
}