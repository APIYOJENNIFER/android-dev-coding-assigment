package com.ensibuuko.android_dev_coding_assigment.ui.postdetails

import androidx.lifecycle.*
import com.ensibuuko.android_dev_coding_assigment.data.Comments
import com.ensibuuko.android_dev_coding_assigment.data.CommentsDao
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.data.PostsDao
import com.ensibuuko.android_dev_coding_assigment.network.CommentsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postsDao: PostsDao,
    api: CommentsApi,
    private val commentsDao: CommentsDao,
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
    private var postId = state.get<Int>("postId") ?: post?.id ?: 0
        set(value) {
            field = value
            state["postId"] = value
        }

    private val commentsLiveData = MutableLiveData<List<Comments>>()
    val comments: LiveData<List<Comments>> = commentsLiveData

    init {
        viewModelScope.launch {
            val comments = api.getComments(postId)
            commentsLiveData.value = comments
        }
    }

//    var commentBody = state.get<String>("commentBody") ?: comments ?: ""
//        set(value) {
//            field = value
//            state["postBody"] = value
//        }

    fun onAddEditCommentClick() {
//save or update comment

    }

    private fun addComment(comments: Comments) = viewModelScope.launch {
        commentsDao.insert(comments)
    }

    private val commentsEventChannel = Channel<CommentsEvent>()
    val commentsEvent = commentsEventChannel.receiveAsFlow()


    fun onEditCommentClicked(comments: Comments) = viewModelScope.launch {
        commentsEventChannel.send(CommentsEvent.EditComment(comments))
    }

    fun onDeleteCommentClicked(comments: Comments) = viewModelScope.launch {
        commentsDao.delete(comments)
    }

    sealed class CommentsEvent {
        data class EditComment(val comments: Comments) : CommentsEvent()
    }
}