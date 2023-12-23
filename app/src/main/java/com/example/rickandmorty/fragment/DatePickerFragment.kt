package com.example.rickandmorty.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.rickandmorty.R

class DatePickerFragment(
    private val currentDay: Int?, private val currentMonth: Int?, private val currentYear: Int?,
    val onClickListener: (day: Int, month: Int, year: Int) -> Unit
) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private var year: Int? = null
    private var month: Int? = null
    private var day: Int? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()

        if (currentYear == null || currentMonth == null || currentDay == null) {
            year = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)

        } else {
            year = currentYear
            month = currentMonth
            day = currentDay
        }

        val picker =
            DatePickerDialog(requireContext(), R.style.datePicker, this, year!!, month!!, day!!)
        picker.datePicker.maxDate = c.timeInMillis
        return picker
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        onClickListener(day, month, year)
    }
}