package com.ensibuuko.android_dev_coding_assigment.ui.postdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.data.Comments
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentPostDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment(R.layout.fragment_post_details), CommentsAdapter.OnItemClickListener {
    private val viewModel: PostDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostDetailsBinding.bind(view)
        val commentsAdapter = CommentsAdapter(this)

        binding.apply {
            tvPostTitle.text = viewModel.postTitle
            tvPostBody.text = viewModel.postBody

            /**
             * comments
             */

            rvComments.apply {
                adapter = commentsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            etAddComment.text
        }

        viewModel.comments.observe(viewLifecycleOwner){
            commentsAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.commentsEvent.collect{
                when(it){
                    is PostDetailsViewModel.CommentsEvent.EditComment -> {
                        binding.apply {
                            etAddComment.setText(it.comments.body)
                        }
                    }
                    is PostDetailsViewModel.CommentsEvent.ShowCommentDeletedConfirmationMessage -> {
                        Snackbar.make(requireView(), it.msg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onEditItemClick(comments: Comments) {
        viewModel.onEditCommentClicked(comments)
    }

    override fun onDeleteItemClick(comments: Comments) {
        viewModel.onDeleteCommentClicked(comments)
    }
}