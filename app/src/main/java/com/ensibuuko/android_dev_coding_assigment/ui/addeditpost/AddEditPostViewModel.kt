package com.ensibuuko.android_dev_coding_assigment.ui.addeditpost

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.data.PostsDao
import com.ensibuuko.android_dev_coding_assigment.ui.ADD_POST_RESULT_OK
import com.ensibuuko.android_dev_coding_assigment.ui.EDIT_POST_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPostViewModel @Inject constructor(
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

    private val addEditPostEventChannel = Channel<AddEditPostEvent>()
    val addEditPostEvent = addEditPostEventChannel.receiveAsFlow()

    fun onPostBtnClick() {
        if (postTitle.isBlank()) {
            showInvalidInputMessage("Title cannot be blank")
            return
        }

        if (postBody.isBlank()) {
            showInvalidInputMessage("Post body cannot be blank")
            return
        }

        if (post != null) {
            val updatedPost = post.copy(title = postTitle, body = postBody)
            updatePost(updatedPost)
        } else {
            val newPost = Posts(title = postTitle, body = postBody, userId = 1)
            createPost(newPost)
        }
    }

    private fun createPost(posts: Posts) = viewModelScope.launch {
        postsDao.insert(posts)
        addEditPostEventChannel.send(AddEditPostEvent.NavigateBackWithResult(ADD_POST_RESULT_OK))
    }

    private fun updatePost(posts: Posts) = viewModelScope.launch {
        postsDao.update(posts)
        addEditPostEventChannel.send(AddEditPostEvent.NavigateBackWithResult(EDIT_POST_RESULT_OK))
    }

    private fun showInvalidInputMessage(msg: String) = viewModelScope.launch {
        addEditPostEventChannel.send(AddEditPostEvent.ShowInvalidInputMessage(msg))
    }

    sealed class AddEditPostEvent {
        data class ShowInvalidInputMessage(val message: String) : AddEditPostEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditPostEvent()
    }
}