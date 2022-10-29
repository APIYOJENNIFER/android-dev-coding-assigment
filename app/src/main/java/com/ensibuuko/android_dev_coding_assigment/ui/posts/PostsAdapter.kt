package com.ensibuuko.android_dev_coding_assigment.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.databinding.ItemPostsBinding

class PostsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Posts, PostsAdapter.PostsViewHolder>(CallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class PostsViewHolder(private val binding: ItemPostsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val posts = getItem(position)
                        listener.onItemClick(posts)
                    }
                }

                ivEdit.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val posts = getItem(position)
                        listener.onEditItemClick(posts)
                    }
                }

                ivDelete.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val posts = getItem(position)
                        listener.onDeleteItemClick(posts)
                    }
                }
            }
        }

        fun bind(posts: Posts) {
            binding.apply {
                tvPostTitle.text = posts.title
                tvPostBody.text = posts.body
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(posts: Posts)
        fun onEditItemClick(posts: Posts)
        fun onDeleteItemClick(posts: Posts)
    }

    class CallBack : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean =
            oldItem == newItem
    }
}