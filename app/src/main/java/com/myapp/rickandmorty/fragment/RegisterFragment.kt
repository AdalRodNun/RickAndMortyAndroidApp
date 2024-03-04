package com.myapp.rickandmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.databinding.FragmentRegisterBinding
import com.myapp.rickandmorty.repository.RoomRepository
import com.myapp.rickandmorty.utils.Functions.isValidEmail
import com.myapp.rickandmorty.utils.SimpleTextWatcher
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class RegisterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val repository = RoomRepository()

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

        etName.addTextChangedListener(SimpleTextWatcher {
            validateEmptyInputs()
        })

        etEmail.addTextChangedListener(SimpleTextWatcher {
            validateEmptyInputs()
            tilEmail.error = null
        })

        etPassword.addTextChangedListener(SimpleTextWatcher {
            validateEmptyInputs()
            tilPassword.error = null
        })

        etConfirmPassword.addTextChangedListener(SimpleTextWatcher {
            validateEmptyInputs()
            tilConfirmPassword.error = null
        })
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

    // TODO - REFACTOR TO VIEW MODEL
    private fun registerUser() {
        binding.apply {
            lifecycleScope.launch {
                val hashedPassword = BCrypt.hashpw(etPassword.text.toString(), BCrypt.gensalt())

                repository.addUser(
                    User(
                        name = etName.text.toString(),
                        email = etEmail.text.toString(),
                        password = hashedPassword
                    )
                )
            }
        }

        dismiss()
    }

    companion object {
        const val TAG = "RegisterBottomSheetFragment"
    }
}