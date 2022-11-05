package com.ensibuuko.android_dev_coding_assigment.ui.posts

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentWalletDialogDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletDialogFragment : BottomSheetDialogFragment(R.layout.fragment_wallet_dialog_dialog) {

    private lateinit var binding: FragmentWalletDialogDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWalletDialogDialogBinding.bind(view)

        binding.apply {

            btnDeposit.setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "wallet",
                    etDeposit.text.toString()
                )
                dismiss()
            }
        }
    }
}