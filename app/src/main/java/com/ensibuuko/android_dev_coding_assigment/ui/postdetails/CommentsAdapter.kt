package com.ensibuuko.android_dev_coding_assigment.ui.postdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.data.Comments
import com.ensibuuko.android_dev_coding_assigment.databinding.ItemCommentsBinding

class CommentsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Comments, CommentsAdapter.CommentsViewHolder>(CallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding =
            ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CommentsViewHolder(private val binding: ItemCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                ivEdit.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val comments = getItem(position)
                        listener.onEditItemClick(comments)
                    }
                }

                ivDelete.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val comments = getItem(position)
                        listener.onDeleteItemClick(comments)
                    }
                }
            }
        }

        fun bind(comments: Comments) {
            binding.apply {
                tvComment.text = comments.body
                tvCommentEmail.text = comments.email
                tvCommentUsername.text = comments.name
            }
        }
    }

    interface OnItemClickListener {
        fun onEditItemClick(comments: Comments)
        fun onDeleteItemClick(comments: Comments)
    }

    class CallBack : DiffUtil.ItemCallback<Comments>() {
        override fun areItemsTheSame(oldItem: Comments, newItem: Comments): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Comments, newItem: Comments): Boolean =
            oldItem == newItem

    }
}