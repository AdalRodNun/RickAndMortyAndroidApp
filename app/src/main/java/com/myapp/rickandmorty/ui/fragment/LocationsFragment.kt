package com.myapp.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.search.SearchView
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.FragmentLocationsBinding
import com.myapp.rickandmorty.domain.model.LocationR
import com.myapp.rickandmorty.ui.adapter.LocationsPagingAdapter
import com.myapp.rickandmorty.ui.viewModel.GetLocationsViewModel
import com.myapp.rickandmorty.utils.ExtendedFunctions.manageScrollUpButtonView
import com.myapp.rickandmorty.utils.ExtendedFunctions.setOnSearchListener
import com.myapp.rickandmorty.utils.ExtendedFunctions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding
    private lateinit var locationsAdapter: LocationsPagingAdapter

    private val viewModel: GetLocationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRecyclerView()
        setListeners()
        setObservers()
        setOnBackPressed()
    }

    private fun initRecyclerView() {
        locationsAdapter = LocationsPagingAdapter(
            onClickListener = { location -> onItemSelected(location) }
        )
        binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacters.adapter = locationsAdapter

        setAdaptersStates()
        binding.scrollUp.hide()
    }

    private fun setListeners() = with(binding) {
        searchView.setOnSearchListener { query ->
            if (query.isNotEmpty()) viewModel.getLocationsList(name = query.lowercase())
            else viewModel.getLocationsList(name = null)

            searchBar.setText(query)
            searchView.hide()
        }

        searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mn_profile -> {
                    requireContext().toast("Profile")
                    true
                }

                else -> false
            }
        }

        searchView.addTransitionListener { _, _, newState ->
            if (newState === SearchView.TransitionState.SHOWING && scrollUp.isShown) {
                scrollUp.hide()
            }
        }

        btRetry.setOnClickListener {
            val name = searchBar.text.toString()

            if (name.isNotEmpty()) viewModel.getLocationsList(name = name.lowercase())
            else viewModel.getLocationsList(name = null)
        }

        scrollUp.setOnClickListener {
            rvCharacters.smoothScrollToPosition(0)
        }

        rvCharacters.manageScrollUpButtonView(button = scrollUp)
    }

    private fun setObservers() = with(viewModel) {
        onError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        lifecycleScope.launch {
            resultsState.collectLatest {
                it.collectLatest { pagingData ->
                    locationsAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setAdaptersStates() = with(binding) {
        locationsAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    btRetry.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                    rvCharacters.visibility = View.GONE
                }

                is LoadState.Error -> {
                    btRetry.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                    rvCharacters.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    btRetry.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    rvCharacters.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onItemSelected(location: LocationR) {
        requireContext().toast(location.name)
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.searchView.isShowing) {
                        binding.searchView.hide()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        )
    }
}