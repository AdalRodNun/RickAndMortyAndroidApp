package com.example.rickandmorty.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentHomeBinding
import com.example.rickandmorty.room.models.Person
import com.example.rickandmorty.room.repository.RoomRepository
import com.example.rickandmorty.viewModel.HomeFragmentViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var textWatcher: TextWatcher
    private val repository = RoomRepository()
    private val viewModel: HomeFragmentViewModel by viewModels()
    private var picture: Bitmap? = null
    private var age: Int? = null

    private var currentYear: Int? = null
    private var currentMonth: Int? = null
    private var currentDay: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initTextWatcher()
        setObservers()
        setListeners()

        return binding.root
    }

    private fun initTextWatcher() {
        textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateFields()
            }
        }
    }

    private fun setObservers() {
        viewModel.enabledButton.observe(viewLifecycleOwner) { enabledButton ->
            if (enabledButton) {
                binding.addButton.isEnabled = enabledButton
            } else {
                binding.addButton.isEnabled = enabledButton
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            addButton.setOnClickListener {
                when {
                    nameTxt.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Name esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    nameTxt.text.toString().length > 20 -> Toast.makeText(
                        requireContext(),
                        "El campo Name no puede ser mayor a 20 caracteres",
                        Toast.LENGTH_LONG
                    ).show()

                    birthdayTxt.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Birthday esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    addressTxt.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Address esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    phoneTxt.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Phone number esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    phoneTxt.text.toString().isEmpty() -> Toast.makeText(
                        requireContext(),
                        "El campo Name no puede ser mayor a 10 caracteres",
                        Toast.LENGTH_LONG
                    ).show()

                    picture == null -> Toast.makeText(
                        requireContext(),
                        "El campo fotografia esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> {
                        saveInfo()
                    }
                }
            }

            birthdayTxt.setOnClickListener {
                showDatePicker()
            }

            cameraButton.setOnClickListener {
                takePhoto()
            }

            nameTxt.addTextChangedListener(textWatcher)
            addressTxt.addTextChangedListener(textWatcher)
            phoneTxt.addTextChangedListener(textWatcher)
            birthdayTxt.addTextChangedListener(textWatcher)
        }
    }

    private fun showDatePicker() {
        val datePickerFgm =
            DatePickerFragment(currentDay, currentMonth, currentYear) { day, month, year ->
                onDateSelected(day, month, year)
            }
        datePickerFgm.show(parentFragmentManager, "datePickerFragment")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        currentYear = year
        currentMonth = month
        currentDay = day

        val dateString = "$day/${month + 1}/$year"
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = format.parse(dateString)

        age = calculateAge(date)
        binding.birthdayTxt.setText(dateString)
    }

    private fun calculateAge(birthday: Date): Int {
        val today = Date(System.currentTimeMillis())

        val difference = today.time - birthday.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val years = days / 365

        return years.toInt()
    }

    private fun takePhoto() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                111
            )

        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        binding.apply {
            if (requestCode == 101) {
                if (data?.getParcelableExtra<Bitmap>("data") != null) {
                    picture = data.getParcelableExtra("data")
                    cameraButton.setImageBitmap(picture)
                    validateFields()
                }
            }
        }
    }

    private fun validateFields() {
        binding.apply {
            viewModel.validateFields(
                name = nameTxt.text.toString().isNotEmpty(),
                address = addressTxt.text.toString().isNotEmpty(),
                phone = phoneTxt.text.toString().isNotEmpty(),
                birthday = birthdayTxt.text.toString().isNotEmpty(),
                photo = picture != null
            )
        }
    }

    private fun saveInfo() {
        binding.apply {
            lifecycleScope.launch {
                repository.addPerson(
                    Person(
                        id = 0,
                        photo = bitmapToString(picture),
                        name = nameTxt.text.toString(),
                        age = age!!,
                        address = addressTxt.text.toString(),
                        phoneNumber = phoneTxt.text.toString(),
                        hobbies = hobbiesTxt.text.toString()
                    )
                )
            }
        }

        cleanFields()
    }

    private fun bitmapToString(image: Bitmap?): String {
        val string = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 40, string)
        val compressedString = string.toByteArray()
        return Base64.encodeToString(compressedString, Base64.DEFAULT)
    }

    private fun cleanFields() {
        binding.apply {
            nameTxt.setText("")
            birthdayTxt.setText("")
            addressTxt.setText("")
            phoneTxt.setText("")
            hobbiesTxt.setText("")
            age = null
            picture = null
            cameraButton.setImageResource(R.drawable.ic_photo)
            currentDay = null
            currentMonth = null
            currentYear = null
        }
    }
}