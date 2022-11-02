package com.ensibuuko.android_dev_coding_assigment.ui.profile

import androidx.lifecycle.*
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.data.User
import com.ensibuuko.android_dev_coding_assigment.network.UserApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    api: UserApi,
    private val state: SavedStateHandle
) : ViewModel() {
    private val post = state.get<Posts>("user")

    private var userId = state.get<Int>("userId") ?: post?.userId ?: 0
        set(value) {
            field = value
            state["userId"] = value
        }

    private val userLiveData = MutableLiveData<User>()
    val users: LiveData<User> = userLiveData

    init {
        viewModelScope.launch {
            val users  = api.getUser(userId)
            userLiveData.value = users
        }
    }


}