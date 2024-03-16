package com.myapp.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.databinding.FragmentRegisterBinding
import com.myapp.rickandmorty.ui.viewModel.RegisterViewModel
import com.myapp.rickandmorty.utils.ExtendedFunctions.addSimpleTextChangedListener
import com.myapp.rickandmorty.utils.Functions.isValidEmail
import com.myapp.rickandmorty.utils.SimpleTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        dialog?.let {
            val sheet = it as BottomSheetDialog
            sheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        init()

        return binding.root
    }

    private fun init() {
        setListeners()
        getObservers()
        manageRegisterButton(isEnabled = false)
    }

    private fun manageRegisterButton(isEnabled: Boolean) {
        binding.btRegister.isEnabled = isEnabled
    }

    private fun setListeners() = with(binding) {
        btRegister.setOnClickListener {
            if (validateInputs()) {
                registerUser()
            }
        }

        etName.addSimpleTextChangedListener {
            validateEmptyInputs()
        }

        etEmail.addSimpleTextChangedListener {
            validateEmptyInputs()
            tilEmail.error = null
        }

        etPassword.addTextChangedListener(SimpleTextWatcher {
            validateEmptyInputs()
            tilPassword.error = null
        })

        etConfirmPassword.addTextChangedListener(SimpleTextWatcher {
            validateEmptyInputs()
            tilConfirmPassword.error = null
        })
    }

    private fun getObservers() = with(viewModel) {
        saved.observe(viewLifecycleOwner) {
            if (it) {
                dismiss()
            }
        }
    }

    private fun validateInputs(): Boolean = with(binding) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmationPassword = etConfirmPassword.text.toString()
        var valid = true

        if (isValidEmail(email)) {
            tilEmail.error = getString(R.string.invalid_email_error)
            valid = false
        }

        if (password.length < 5) {
            tilPassword.error = getString(R.string.password_long_error)
            valid = false
        }

        if (password != confirmationPassword) {
            tilConfirmPassword.error = getString(R.string.passwords_match_error)
            valid = false
        }

        return valid
    }

    private fun validateEmptyInputs() = with(binding) {
        if (etName.text.toString().isNotEmpty() &&
            etEmail.text.toString().isNotEmpty() &&
            etPassword.text.toString().isNotEmpty() &&
            etConfirmPassword.text.toString().isNotEmpty()
        ) {
            manageRegisterButton(isEnabled = true)
        }
    }

    private fun registerUser() = with(binding) {
        val user = User(
            name = etName.text.toString(),
            email = etEmail.text.toString(),
            password = etPassword.text.toString()
        )

        viewModel.saveUser(user)
    }

    companion object {
        const val TAG = "RegisterBottomSheetFragment"
    }
}