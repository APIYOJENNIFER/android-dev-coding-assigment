package com.ensibuuko.android_dev_coding_assigment.ui.posts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.Posts
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentPostsBinding
import com.ensibuuko.android_dev_coding_assigment.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts), PostsAdapter.OnItemClickListener {

    private val postsViewModel: PostsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostsBinding.bind(view)
        val postsAdapter = PostsAdapter(this)

        binding.apply {
            rvPosts.apply {
                adapter = postsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            fab.setOnClickListener {
                postsViewModel.onAddPostClick()
            }

            btnDeposit.setOnClickListener {
                postsViewModel.onDepositButtonClick()
            }
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            postsViewModel.onAddEditResult(result)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("wallet")
            ?.observe(viewLifecycleOwner) {
                postsViewModel.setWalletBal(it)
            }

        postsViewModel.wallet.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvWalletBalance.text = it.amount
            }
        }

        postsViewModel.posts.observe(viewLifecycleOwner) {
            postsAdapter.submitList(it.data)

            binding.progressCircular.isVisible = it is Resource.Loading && it.data.isNullOrEmpty()
            binding.tvError.isVisible = it is Resource.Error && it.data.isNullOrEmpty()
            binding.tvError.text = it.error?.localizedMessage
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            postsViewModel.postEvent.collect {
                when (it) {
                    is PostsViewModel.PostEvent.NavigateToAddPostScreen -> {
                        val action = PostsFragmentDirections.actionPostsFragmentToAddEditFragment(
                            "Add Post",
                            null
                        )
                        findNavController().navigate(action)
                    }
                    is PostsViewModel.PostEvent.NavigateToEditPostScreen -> {
                        val action = PostsFragmentDirections.actionPostsFragmentToAddEditFragment(
                            "Edit Post",
                            it.posts
                        )
                        findNavController().navigate(action)
                    }
                    is PostsViewModel.PostEvent.NavigateToPostDetailsScreen -> {
                        val action =
                            PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(it.posts)
                        findNavController().navigate(action)
                    }

                    is PostsViewModel.PostEvent.ShowPostCreatedConfirmationMessage -> {
                        Snackbar.make(requireView(), it.msg, Snackbar.LENGTH_SHORT).show()
                    }

                    is PostsViewModel.PostEvent.NavigateToProfileScreen -> {
                        val action =
                            PostsFragmentDirections.actionPostsFragmentToProfileFragment(it.posts)
                        findNavController().navigate(action)
                    }
                    is PostsViewModel.PostEvent.NavigateToAddDepositBottomSheet -> {
                        val action =
                            PostsFragmentDirections.actionPostFragmentToWalletDialogFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onItemClick(posts: Posts) {
        postsViewModel.onPostClicked(posts)
    }

    override fun onEditItemClick(posts: Posts) {
        postsViewModel.onEditPostClicked(posts)
    }

    override fun onDeleteItemClick(posts: Posts) {
        postsViewModel.onPostDeleted(posts)
    }

    override fun onViewProfileClick(posts: Posts) {
        postsViewModel.onViewProfileClicked(posts)
    }

}