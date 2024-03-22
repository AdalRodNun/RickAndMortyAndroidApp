package com.myapp.rickandmorty.data.enums

import com.myapp.rickandmorty.R

enum class StatusEnum(val color: Int, val str: String) {
    Alive(R.color.alive, "Alive"),
    Dead(R.color.dead, "Dead"),
    Unknown(R.color.unknown, "Unknown");

    companion object {
        //fun valueOf(value: String) = entries.find { it.str == value } ?: Unknown

        fun String?.toEnum() = entries.find { it.name == this } ?: Unknown
    }
}
