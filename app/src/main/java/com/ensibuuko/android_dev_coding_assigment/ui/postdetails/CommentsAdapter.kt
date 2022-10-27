package com.ensibuuko.android_dev_coding_assigment.ui.postdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.Comments

class CommentsAdapter(private val context: Context, var commentsLists: ArrayList<Comments>) :
    RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_comments, parent, false)
        return CommentsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comments = commentsLists[position]
        holder.setData(comments, position)
    }

    override fun getItemCount(): Int = commentsLists.size

    inner class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserName = itemView.findViewById<TextView>(R.id.tv_comment_username)
        private val tvComment = itemView.findViewById<TextView>(R.id.tv_comment)
        private val ivEdit = itemView.findViewById<ImageView>(R.id.iv_edit)
        private val ivDelete = itemView.findViewById<ImageView>(R.id.iv_delete)

        private var currentPosition: Int = -1
        private var comments: Comments? = null

        fun setData(comments: Comments, position: Int) {
            this.currentPosition = position
            this.comments = comments

            tvUserName.text = comments.name
            tvComment.text = comments.body
        }
    }
}