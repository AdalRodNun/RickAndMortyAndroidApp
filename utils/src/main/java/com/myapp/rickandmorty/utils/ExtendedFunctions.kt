package com.myapp.rickandmorty.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast

object ExtendedFunctions {

    /**
     * This extension show toast if not empty
     */
    fun Context.toast(message: String) {
        if (message.isNotEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This function changes activity
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
     * This extensions returns the package info
     */
    fun PackageManager.getPackageInfoCompat(packageName: String): PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION") getPackageInfo(packageName, 0)
        }
    }
}