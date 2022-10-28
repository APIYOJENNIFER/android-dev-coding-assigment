package com.ensibuuko.android_dev_coding_assigment.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ensibuuko.android_dev_coding_assigment.data.PostsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsDao: PostsDao
) : ViewModel() {
    val posts = postsDao.getPosts().asLiveData()
}