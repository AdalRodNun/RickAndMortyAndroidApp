package com.example.onboardingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.onboardingapp.databinding.FragmentInformationDialogBinding

class InformationDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentInformationDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationDialogBinding.inflate(inflater, container, false)

        setListeners()
        init()

        return binding.root
    }

    private fun init() {
        val bundle = arguments

        binding.addressText.text = bundle?.getString("address")
        binding.ageText.text = "${bundle?.getInt("age")} años"
        binding.hobbiesText.text =
            if (bundle?.getString("hobbies") == "") "No hobbies" else bundle?.getString("hobbies")
        binding.numberText.text = bundle?.getString("number")
        binding.nameTxt.text = bundle?.getString("name")
    }

    private fun setListeners() {
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }
}