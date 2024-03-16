package com.myapp.rickandmorty.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ActivityContainerBinding
import com.myapp.rickandmorty.ui.fragment.CharactersFragment
import com.myapp.rickandmorty.ui.fragment.EpisodesFragment
import com.myapp.rickandmorty.ui.fragment.LocationsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setDefault()
        setListeners()
    }

    private fun setDefault() {
        replaceFragment(CharactersFragment())
        binding.bnvMain.selectedItemId = R.id.mn_characters
    }

    private fun setListeners() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mn_episodes -> {
                    replaceFragment(EpisodesFragment())
                    true
                }

                R.id.mn_characters -> {
                    replaceFragment(CharactersFragment())
                    true
                }

                R.id.mn_locations -> {
                    replaceFragment(LocationsFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.fl_container, fragment)
        transition.commit()
    }
}