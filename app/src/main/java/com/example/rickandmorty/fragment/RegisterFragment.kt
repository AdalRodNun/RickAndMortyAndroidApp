package com.example.rickandmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.databinding.FragmentRegisterBinding
import com.example.rickandmorty.room.models.User
import com.example.rickandmorty.room.repository.RoomRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

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

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.apply {
            registerButton.setOnClickListener {
                when {
                    nameTxt.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Name esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    nameTxt.text.toString().length > 10 -> Toast.makeText(
                        requireContext(),
                        "El campo Name ocupa ser maximo 10 caracteres",
                        Toast.LENGTH_LONG
                    ).show()

                    emailTxt.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Email esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    !(android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt.text.toString())
                        .matches()) -> Toast.makeText(
                        requireContext(),
                        "El campo Email es invalido",
                        Toast.LENGTH_LONG
                    ).show()

                    passwordText.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Password esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    passwordText.text.toString().length < 5 || passwordText.text.toString().length > 10 -> Toast.makeText(
                        requireContext(),
                        "El campo Password ocupa ser de minimo 5 y maximo 10 caracteres",
                        Toast.LENGTH_LONG
                    ).show()

                    confirmPasswordText.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Confirm password esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    confirmPasswordText.text.toString().length < 5 || passwordText.text.toString().length > 10 -> Toast.makeText(
                        requireContext(),
                        "El campo Confirm password ocupa ser de minimo 5 y maximo 10 caracteres",
                        Toast.LENGTH_LONG
                    ).show()

                    passwordText.text.toString() != confirmPasswordText.text.toString() -> Toast.makeText(
                        requireContext(),
                        "Las contraseñas son diferentes",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> {
                        registerUser()
                    }
                }
            }

            toolbar.setNavigationOnClickListener {
                dismiss()
            }
        }
    }

    private fun registerUser() {
        binding.apply {
            lifecycleScope.launch {
                repository.addUser(
                    User(
                        id = 0,
                        name = nameTxt.text.toString(),
                        email = emailTxt.text.toString(),
                        password = passwordText.text.toString()
                    )
                )
            }
        }

        cleanFields()
    }

    private fun cleanFields() = with(binding) {
        nameTxt.setText("")
        emailTxt.setText("")
        passwordText.setText("")
        confirmPasswordText.setText("")
    }
}