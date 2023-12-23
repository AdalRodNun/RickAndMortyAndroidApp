package com.example.rickandmorty.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityContainerBinding
import com.example.rickandmorty.fragment.HomeFragment
import com.example.rickandmorty.fragment.ListFragment
import com.example.rickandmorty.fragment.ServiceFragment

class ContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        init()
    }

    private fun init() {
        setListeners()

        binding.bottomNavigation.selectedItemId = R.id.ic_home
    }

    private fun setListeners() {
        showBottomNavigation()
    }

    private fun showBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
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
                    replaceFragment(ServiceFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.frame_layout, fragment)
        transition.commit()
    }
}