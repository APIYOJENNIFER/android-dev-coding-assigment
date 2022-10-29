package com.ensibuuko.android_dev_coding_assigment.ui.addeditpost

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentAddEditBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditFragment : Fragment(R.layout.fragment_add_edit) {

    private val viewModel: AddEditPostViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddEditBinding.bind(view)
        binding.apply {
            etAddEditTitle.setText(viewModel.postTitle)
            etAddEditBody.setText(viewModel.postBody)

            etAddEditTitle.addTextChangedListener {
                viewModel.postTitle = it.toString()
            }

            etAddEditBody.addTextChangedListener {
                viewModel.postBody = it.toString()
            }

            btnPost.setOnClickListener {
                viewModel.onPostBtnClick()
            }

            btnCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditPostEvent.collect {
                when (it) {
                    is AddEditPostViewModel.AddEditPostEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), it.message, Snackbar.LENGTH_LONG).show()
                    }

                    is AddEditPostViewModel.AddEditPostEvent.NavigateBackWithResult -> {
                        binding.apply {
                            etAddEditTitle.clearFocus()
                            etAddEditBody.clearFocus()
                        }
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to it.result)
                        )
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}