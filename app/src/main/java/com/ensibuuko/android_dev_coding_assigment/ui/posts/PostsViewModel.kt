package com.ensibuuko.android_dev_coding_assigment.ui.posts

import androidx.lifecycle.*
import com.ensibuuko.android_dev_coding_assigment.data.*
import com.ensibuuko.android_dev_coding_assigment.util.Constants.ADD_POST_RESULT_OK
import com.ensibuuko.android_dev_coding_assigment.util.Constants.EDIT_POST_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsDao: PostsDao,
    repository: PostsRepository,
    private val walletDao: WalletDao,
) : ViewModel() {

    val posts = repository.getPosts().asLiveData()

    private val postEventChannel = Channel<PostEvent>()
    val postEvent = postEventChannel.receiveAsFlow()

    private val walletMutableLiveData = MutableLiveData<Wallet>()
    val wallet: LiveData<Wallet> = walletMutableLiveData

    init {
        viewModelScope.launch {
            val wallet = walletDao.getWallet()
            walletMutableLiveData.value = wallet
        }
    }

    fun setWalletBal(depositAmount: String) {
        if (depositAmount.isEmpty()) {
            showInformationMessage("Amount cannot be empty")
            return
        }

        if (wallet.value != null) {
            val amt = depositAmount.toInt()
            val oldAmt = wallet.value?.amount?.toInt()
            val totalAmt = amt + oldAmt!!
            val totalAmtStr = totalAmt.toString()

            val updatedWallet = wallet.value!!.copy(amount = totalAmtStr)
            updateWallet(updatedWallet)
        } else {
            val newWallet = Wallet(amount = depositAmount, userId = 1)
            createWallet(newWallet)
        }
    }

    private fun createWallet(wallet: Wallet) = viewModelScope.launch {
        walletDao.insert(wallet)
        showInformationMessage("Wallet Created")
    }

    private fun updateWallet(wallet: Wallet) = viewModelScope.launch {
        walletDao.update(wallet)
        showInformationMessage("Wallet Balance Updated")
    }

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
            ADD_POST_RESULT_OK -> showInformationMessage("Post Added")
            EDIT_POST_RESULT_OK -> showInformationMessage("Post Updated")
        }
    }

    fun onDepositButtonClick() = viewModelScope.launch {
        postEventChannel.send(PostEvent.NavigateToAddDepositBottomSheet)
    }

    private fun showInformationMessage(msg: String) = viewModelScope.launch {
        postEventChannel.send(PostEvent.ShowPostCreatedConfirmationMessage(msg))
    }

    sealed class PostEvent {
        object NavigateToAddPostScreen : PostEvent()
        data class NavigateToEditPostScreen(val posts: Posts) : PostEvent()
        data class NavigateToPostDetailsScreen(val posts: Posts) : PostEvent()
        data class NavigateToProfileScreen(val posts: Posts) : PostEvent()
        data class ShowPostCreatedConfirmationMessage(val msg: String) : PostEvent()
        object NavigateToAddDepositBottomSheet : PostEvent()
    }
}