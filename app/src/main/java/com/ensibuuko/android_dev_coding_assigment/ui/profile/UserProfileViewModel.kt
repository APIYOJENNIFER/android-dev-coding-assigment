package com.ensibuuko.android_dev_coding_assigment.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensibuuko.android_dev_coding_assigment.data.User
import com.ensibuuko.android_dev_coding_assigment.network.UserApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    api: UserApi,
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