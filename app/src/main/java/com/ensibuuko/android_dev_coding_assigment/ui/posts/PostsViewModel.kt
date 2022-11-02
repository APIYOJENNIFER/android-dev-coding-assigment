package com.ensibuuko.android_dev_coding_assigment.ui.posts

import androidx.lifecycle.*
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.data.PostsDao
import com.ensibuuko.android_dev_coding_assigment.data.PostsRepository
import com.ensibuuko.android_dev_coding_assigment.network.PostApi
import com.ensibuuko.android_dev_coding_assigment.ui.ADD_POST_RESULT_OK
import com.ensibuuko.android_dev_coding_assigment.ui.EDIT_POST_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsDao: PostsDao,
    repository: PostsRepository
) : ViewModel() {
    val posts = repository.getPosts().asLiveData()

    private val postEventChannel = Channel<PostEvent>()
    val postEvent = postEventChannel.receiveAsFlow()

    fun onPostClicked(posts: Posts) = viewModelScope.launch {
        postEventChannel.send(PostEvent.NavigateToPostDetailsScreen(posts))
    }

    fun onEditPostClicked(posts: Posts) = viewModelScope.launch {
        postEventChannel.send(PostEvent.NavigateToEditPostScreen(posts))
    }

    fun onPostDeleted(posts: Posts) = viewModelScope.launch {
        postsDao.delete(posts)
    }

    fun onAddPostClick() = viewModelScope.launch {
        postEventChannel.send(PostEvent.NavigateToAddPostScreen)
    }

    fun onViewProfileClicked(posts: Posts) = viewModelScope.launch {
        postEventChannel.send(PostEvent.NavigateToProfileScreen(posts))
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_POST_RESULT_OK -> showPostCreatedConfirmationMessage("Post Added")
            EDIT_POST_RESULT_OK -> showPostCreatedConfirmationMessage("Post Updated")
        }
    }

    private fun showPostCreatedConfirmationMessage(msg: String) = viewModelScope.launch {
        postEventChannel.send(PostEvent.ShowPostCreatedConfirmationMessage(msg))
    }

    sealed class PostEvent {
        object NavigateToAddPostScreen : PostEvent()
        data class NavigateToEditPostScreen(val posts: Posts) : PostEvent()
        data class NavigateToPostDetailsScreen(val posts: Posts) : PostEvent()
        data class NavigateToProfileScreen(val posts: Posts) : PostEvent()
        data class ShowPostCreatedConfirmationMessage(val msg: String) : PostEvent()
    }
}