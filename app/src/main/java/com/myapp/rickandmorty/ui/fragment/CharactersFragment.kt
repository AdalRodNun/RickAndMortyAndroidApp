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
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.rickandmorty.databinding.FragmentCharactersBinding
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.ui.adapter.CharactersPagingAdapter
import com.myapp.rickandmorty.ui.viewModel.GetCharactersViewModel
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
        binding.recylerService.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerService.adapter = charactersAdapter
    }

    private fun setListeners() = with(binding) {
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            val text = searchView.text.toString()

            if (text.isNotEmpty()) viewModel.getCharactersList(name = text.lowercase())
            else viewModel.getCharactersList(name = null)

            searchBar.setText(text)
            searchView.hide()
            false
        }

        searchView.setOnMenuItemClickListener {
            Log.e("Test", it.toString())
            false
        }
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

    private fun onItemSelected(character: CharacterR) {
        Toast.makeText(requireContext(), character.name, Toast.LENGTH_LONG).show()
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.searchView.isShowing) {
                        binding.searchView.hide()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })
    }
}