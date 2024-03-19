package com.myapp.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.myapp.rickandmorty.domain.model.CharacterR
import com.myapp.rickandmorty.ui.viewModel.CharacterDetailViewModel
import com.myapp.rickandmorty.utils.ExtendedFunctions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private val args by navArgs<CharacterDetailFragmentArgs>()

    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setListeners()
        getObservers()
        getArgument()
    }

    private fun getArgument() {
        val id = args.characterID
        viewModel.getCharacterInfo(characterID = id)
    }

    private fun setListeners() = with(binding) {
        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mn_search -> {
                    findNavController().navigate(R.id.action_characterDetailFragment_to_charactersFragment)
                    true
                }

                R.id.mn_more -> {
                    requireContext().toast("More")
                    true
                }

                else -> false
            }
        }
    }

    private fun getObservers() = with(viewModel) {
        characterResponse.observe(viewLifecycleOwner) { character ->
            setInformationToView(character)
        }
    }

    private fun setInformationToView(character: CharacterR) = with(binding) {
        character.apply {
            Glide.with(requireContext()).load(image).into(ivCharacter)
            tvStatus.text = status.str
            tvName.text = name
            tvSpecies.text = species
            tvGender.text = gender
            tvOrigin.text = origin
            tvLocation.text = location
            tvEpisodes.text = episodes?.size.toString()
        }
    }
}