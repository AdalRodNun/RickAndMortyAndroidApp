package com.myapp.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.search.SearchView.TransitionState
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.FragmentCharactersBinding
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.ui.adapter.CharactersPagingAdapter
import com.myapp.rickandmorty.ui.adapter.LoadingAdapter
import com.myapp.rickandmorty.ui.viewModel.GetCharactersViewModel
import com.myapp.rickandmorty.utils.ExtendedFunctions.manageScrollUpButtonView
import com.myapp.rickandmorty.utils.ExtendedFunctions.setOnBackPressed
import com.myapp.rickandmorty.utils.ExtendedFunctions.setOnSearchListener
import com.myapp.rickandmorty.utils.ExtendedFunctions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersPagingAdapter

    private val viewModel: GetCharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
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

    private fun initRecyclerView() = with(binding) {
        charactersAdapter = CharactersPagingAdapter(
            onClickListener = { character -> onItemSelected(character) }
        )
        rvCharacters.layoutManager = LinearLayoutManager(requireContext())

        rvCharacters.adapter = charactersAdapter.withLoadStateHeaderAndFooter(
            header = LoadingAdapter { charactersAdapter.retry() },
            footer = LoadingAdapter { charactersAdapter.retry() }
        )

        setAdapterStatesListener()
    }

    private fun setListeners() = with(binding) {
        searchView.setOnSearchListener { query ->
            if (query.isNotEmpty()) viewModel.getCharactersList(name = query.lowercase())
            else viewModel.getCharactersList(name = null)

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
            if (newState === TransitionState.SHOWING && btScrollUp.isShown) {
                btScrollUp.hide()
            }
        }

        /*btRetry.setOnClickListener {
            val name = searchBar.text.toString()

            if (name.isNotEmpty()) viewModel.getCharactersList(name = name.lowercase())
            else viewModel.getCharactersList(name = null)
        }*/

        btRetry.setOnClickListener { charactersAdapter.retry() }

        btScrollUp.setOnClickListener {
            rvCharacters.smoothScrollToPosition(0)
        }

        rvCharacters.manageScrollUpButtonView(button = btScrollUp)
    }

    private fun setObservers() = with(viewModel) {
        lifecycleScope.launch {
            resultsState.collectLatest {
                it.collectLatest { pagingData ->
                    charactersAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setAdapterStatesListener() = with(binding) {
        charactersAdapter.addLoadStateListener { loadState ->

            /*val isEmptyList = loadState.refresh is LoadState.NotLoading && charactersAdapter.itemCount == 0
            showEmptyList(isEmptyList)*/

            // Only show the list if refresh succeeds
            rvCharacters.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh
            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails
            btRetry.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    private fun onItemSelected(character: CharacterR) {
        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                characterID = character.id ?: 0
            )
        )
    }

    private fun setOnBackPressed() {
        this.setOnBackPressed {
            if (binding.searchView.isShowing) {
                binding.searchView.hide()
            } else {
                it.isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}