package com.myapp.rickandmorty.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.myapp.rickandmorty.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private val args by navArgs<CharacterDetailFragmentArgs>()

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
        val name = args.characterName
        val id = args.characterID
        binding.tvDummy.text = "$id - $name"
    }
}