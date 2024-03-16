package com.myapp.rickandmorty.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * This class simplifies text watcher implementation on activities and fragments.
 */
class SimpleTextWatcher(private val onTextChangedAction: (String?) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //Implementation not needed
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChangedAction(s.toString())
    }
    override fun afterTextChanged(s: Editable?) {
        //Implementation not needed
    }
}