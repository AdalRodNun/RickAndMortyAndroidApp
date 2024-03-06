package com.myapp.rickandmorty.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ActivityContainerBinding
import com.myapp.rickandmorty.ui.fragment.HomeFragment
import com.myapp.rickandmorty.ui.fragment.ListFragment
import com.myapp.rickandmorty.ui.fragment.ServiceFragment

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
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            manageBottomNavigation(item.itemId)
        }
    }

    private fun manageBottomNavigation(itemId: Int): Boolean {
        return when (itemId) {
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

    private fun replaceFragment(fragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.frame_layout, fragment)
        transition.commit()
    }
}