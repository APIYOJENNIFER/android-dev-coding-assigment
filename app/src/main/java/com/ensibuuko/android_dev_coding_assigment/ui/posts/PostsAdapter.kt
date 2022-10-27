package com.ensibuuko.android_dev_coding_assigment.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.google.android.material.card.MaterialCardView

class PostsAdapter(private val context: Context, var postsList: ArrayList<Posts>) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_posts, parent, false)
        return PostsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val posts = postsList[position]
        holder.setData(posts, position)
    }

    override fun getItemCount(): Int = postsList.size

    inner class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPostTitle = itemView.findViewById<TextView>(R.id.tv_post_title)
        private val tvPostBody = itemView.findViewById<TextView>(R.id.tv_post_body)
        private val ivEdit = itemView.findViewById<ImageView>(R.id.iv_edit)
        private val ivDelete = itemView.findViewById<ImageView>(R.id.iv_delete)
        private val cvPost = itemView.findViewById<MaterialCardView>(R.id.cv_post)

        private var currentPosition: Int = -1
        private var posts: Posts? = null

        fun setData(posts: Posts, position: Int) {
            this.currentPosition = position
            this.posts = posts

            tvPostTitle.text = posts.title
            tvPostBody.text = posts.body
        }
    }
}