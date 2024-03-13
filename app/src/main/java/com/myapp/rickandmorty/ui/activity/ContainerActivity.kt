package com.myapp.rickandmorty.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ActivityContainerBinding
import com.myapp.rickandmorty.ui.fragment.HomeFragment
import com.myapp.rickandmorty.ui.fragment.ListFragment
import com.myapp.rickandmorty.ui.fragment.CharactersFragment
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
        replaceFragment(HomeFragment())
        binding.bnvMain.selectedItemId = R.id.ic_home
    }

    private fun setListeners() {
        /*binding.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.ic_list -> {
                }

                R.id.ic_home -> {
                }

                R.id.ic_service -> {
                }
            }
        }*/

        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_list -> {
                    replaceFragment(ListFragment())
                    true
                }

                R.id.ic_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.ic_service -> {
                    replaceFragment(CharactersFragment())
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