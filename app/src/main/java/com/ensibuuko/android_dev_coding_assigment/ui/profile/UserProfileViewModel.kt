package com.ensibuuko.android_dev_coding_assigment.ui.profile

import androidx.lifecycle.*
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.data.User
import com.ensibuuko.android_dev_coding_assigment.network.UserApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    api: UserApi,
    private val state: SavedStateHandle
) : ViewModel() {

    private val userLiveData = MutableLiveData<User>()
    val currentUser: LiveData<User> = userLiveData

    init {
        viewModelScope.launch {
            val currentUser = api.getUser(1) //get ID dynamically
            userLiveData.value = currentUser
        }
    }


}