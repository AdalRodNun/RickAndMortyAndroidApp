package com.myapp.rickandmorty.data.enums

import androidx.annotation.ColorRes
import com.myapp.rickandmorty.R

enum class StatusEnum(@ColorRes val color: Int, val str: String) {
    Alive(R.color.alive, "Alive"),
    Dead(R.color.dead, "Dead"),
    Unknown(R.color.unknown, "Unknown");

    companion object {
        //fun valueOf(value: String) = entries.find { it.str == value } ?: Unknown

        fun String?.toEnum() = entries.find { it.name == this } ?: Unknown
    }
}
