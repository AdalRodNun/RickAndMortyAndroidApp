package com.myapp.rickandmorty.data.enums

import com.myapp.rickandmorty.style.R

enum class LocationTypeEnum(val icon: Int, val names: ArrayList<String>) {
    Planet(R.drawable.ic_planet, arrayListOf("Planet", "Dwarf planet (Celestial Dwarf)", "Cluster")),
    Place(R.drawable.ic_place, arrayListOf("Spa", "Police Department", "Resort", "Fantasy town", "Customs", "Daycare", "Arcade", "Convention", "Base", "Menagerie", "Artificially generated world", "Acid plant")),
    SpaceStation(R.drawable.ic_station, arrayListOf("Space station", "Death Star", "Spacecraft")),
    Verses(R.drawable.ic_verse, arrayListOf("Microverse", "Miniverse", "Teenyverse")),
    Dimension(R.drawable.ic_dimension, arrayListOf("Dimension")),
    Object(R.drawable.ic_object, arrayListOf("Box", "Elemental Rings", "Machine", "Game")),
    Book(R.drawable.ic_book, arrayListOf("Diegesis", "Non-Diegetic Alternative Reality")),
    Mind(R.drawable.ic_mind, arrayListOf("Memory", "Consciousness", "Dream", "Nightmare")),
    Liquid(R.drawable.ic_water, arrayListOf("Liquid")),
    Hell(R.drawable.ic_fire, arrayListOf("Hell")),
    TV(R.drawable.ic_tv, arrayListOf("TV")),
    Space(R.drawable.ic_space, arrayListOf("Asteroid", "Space")),
    Areas(R.drawable.ic_area, arrayListOf("Country", "Quadrant", "Quasar")),
    Human(R.drawable.ic_human, arrayListOf("Human")),
    Unknown(R.drawable.ic_unknown, arrayListOf("unknown", "Reality"));
    //Mount, Woods

    companion object {
        fun String?.toLocationEnum() = entries.find { it.names.contains(this) } ?: Unknown
    }
}