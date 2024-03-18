package com.myapp.rickandmorty.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.rickandmorty.databinding.FragmentCharactersBinding
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.ui.adapter.CharactersPagingAdapter
import com.myapp.rickandmorty.ui.viewModel.GetCharactersViewModel
import com.myapp.rickandmorty.utils.ExtendedFunctions.manageScrollUpButtonView
import com.myapp.rickandmorty.utils.ExtendedFunctions.setOnSearchListener
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

    private fun initRecyclerView() {
        charactersAdapter = CharactersPagingAdapter(
            onClickListener = { character -> onItemSelected(character) }
        )
        binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacters.adapter = charactersAdapter

        setAdaptersStates()
        binding.scrollUp.hide()
    }

    private fun setListeners() = with(binding) {
        searchView.setOnSearchListener { query ->
            if (query.isNotEmpty()) viewModel.getCharactersList(name = query.lowercase())
            else viewModel.getCharactersList(name = null)

            searchBar.setText(query)
            searchView.hide()
        }

        searchBar.setOnClickListener {
            if(scrollUp.isShown) scrollUp.hide()
        }

        btRetry.setOnClickListener {
            val name = searchBar.text.toString()

            if (name.isNotEmpty()) viewModel.getCharactersList(name = name.lowercase())
            else viewModel.getCharactersList(name = null)
        }

        scrollUp.setOnClickListener {
            rvCharacters.smoothScrollToPosition(0)
        }

        searchView.setOnMenuItemClickListener {
            Log.e("Test", it.toString())
            false
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
                    charactersAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setAdaptersStates() = with(binding) {
        charactersAdapter.addLoadStateListener {
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

    private fun onItemSelected(character: CharacterR) {
        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                characterID = character.id ?: 0
            )
        )
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