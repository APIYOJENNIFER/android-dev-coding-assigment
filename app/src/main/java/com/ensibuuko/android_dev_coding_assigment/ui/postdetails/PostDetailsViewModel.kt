package com.ensibuuko.android_dev_coding_assigment.ui.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.data.PostsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postsDao: PostsDao,
    private val state: SavedStateHandle
) : ViewModel() {
    private val post = state.get<Posts>("post")

    var postTitle = state.get<String>("postTitle") ?: post?.title ?: ""
        set(value) {
            field = value
            state["postTitle"] = value
        }

    var postBody = state.get<String>("postBody") ?: post?.body ?: ""
        set(value) {
            field = value
            state["postBody"] = value
        }
}