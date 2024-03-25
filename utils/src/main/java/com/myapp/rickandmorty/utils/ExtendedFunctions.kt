package com.myapp.rickandmorty.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

object ExtendedFunctions {

    /**
     * This extension show toast if not empty
     */
    fun Context.toast(message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This extension show snackBar if not empty
     */
    fun View.snackBar(message: String, textBt: String = "Ok", onClick: (() -> Unit)? = null) {
        if (message.isNotEmpty()) {
            Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
                .setAction(textBt) {
                    onClick?.invoke()
                }.show()
        }
    }

    /**
     * This extension changes activity
     */
    fun Activity.goActivity(
        activity: Activity,
        finishCurrent: Boolean = false,
        cleanHistoryStack: Boolean = false
    ) {
        val intent = Intent(this, activity::class.java)
        if (cleanHistoryStack) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        if (finishCurrent) {
            finish()
        }
    }

    /**
     * This extension gives to a fragment the ability to manage back pressed
     */
    fun Fragment.setOnBackPressed(callback: (OnBackPressedCallback) -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callback(this)
                }
            }
        )
    }

    /**
     * This extension returns the package info
     */
    fun PackageManager.getPackageInfoCompat(packageName: String): PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            getPackageInfo(packageName, 0)
        }
    }

    /**
     * This extension simplifies text watcher implementation on activities and fragments
     */
    fun TextInputEditText.addSimpleTextChangedListener(listener: (String?) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Implementation not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                //Implementation not needed
            }
        })
    }

    /**
     * This extension simplifies implementation of searchView listener
     */
    fun SearchView.setOnSearchListener(listener: (String) -> Unit) {
        this.editText.setOnEditorActionListener { _, _, _ ->
            val text = this.text.toString()
            listener(text)
            false
        }
    }

    /**
     * This extensions manage the view of scroll up button
     */
    fun RecyclerView.manageScrollUpButtonView(button: ExtendedFloatingActionButton) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 10 && button.isShown) {
                    // going down
                    button.hide()
                }

                if (dy < -10 && !button.isShown) {
                    // going up
                    button.show()
                }

                if (!recyclerView.canScrollVertically(-1) && button.isShown) {
                    // being at the top of recycler
                    button.hide()
                }
            }
        })
    }
}