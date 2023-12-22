package com.example.onboardingapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onboardingapp.adapter.CharactersListAdapter
import com.example.onboardingapp.databinding.FragmentServiceBinding
import com.example.onboardingapp.services.domains.CharacterRDomain
import com.example.onboardingapp.viewModel.GetCharactersViewModel

class ServiceFragment : Fragment() {
    private lateinit var binding: FragmentServiceBinding
    private lateinit var adapter: CharactersListAdapter
    private var charactersList = arrayListOf<CharacterRDomain>()
    private val viewModel: GetCharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(inflater, container, false)

        setListeners()
        setObservers()
        initRecyclerView()
        viewModel.getCharacters()

        return binding.root
    }

    private fun setListeners() {
        //lifecycleScope(Dispatchers.IO).launch {  }
        binding.apply {
            searchButton.setOnClickListener {
                if (searchTxt.text.toString().isEmpty()) {
                    Toast.makeText(context, "El campo Buscar esta vacio", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.getCharactersByName(searchTxt.text.toString().lowercase())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.onError.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        viewModel.getCharactersResponse.observe(viewLifecycleOwner) {
            charactersList.clear()
            charactersList.addAll(it)

            binding.totalTxt.text = "Total ${it.size} characters"
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        adapter = CharactersListAdapter(charactersList)
        binding.recylerService.layoutManager = LinearLayoutManager(context)
        binding.recylerService.adapter = adapter
    }
}