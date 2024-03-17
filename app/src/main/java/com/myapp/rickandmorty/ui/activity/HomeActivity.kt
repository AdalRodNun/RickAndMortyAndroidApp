package com.myapp.rickandmorty.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerHome) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationHome.setupWithNavController(navController)
        setNavigationListeners()
    }

    private fun setNavigationListeners() {
        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            when (destination.id) {
                R.id.charactersFragment -> showNavMenu()
                R.id.episodesFragment -> showNavMenu()
                R.id.locationsFragment -> showNavMenu()
                else -> hideNavMenu()
            }
        }
    }

    private fun hideNavMenu() {
        binding.bottomNavigationHome.visibility = View.GONE
    }

    private fun showNavMenu() {
        binding.bottomNavigationHome.visibility = View.VISIBLE
    }

    /*private fun setDefault() {
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
    }*/
}