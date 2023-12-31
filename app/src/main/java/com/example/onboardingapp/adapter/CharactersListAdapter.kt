package com.example.onboardingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onboardingapp.databinding.ItemCharactersListBinding
import com.example.onboardingapp.services.domains.CharacterRDomain
import com.squareup.picasso.Picasso

class CharactersListAdapter(private var charactersList: MutableList<CharacterRDomain>) :
    RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCharactersListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val characterItem = charactersList[position]

        holder.binding.nameTxt.text = characterItem.name
        Picasso.get().load(characterItem.image).into(holder.binding.cameraButton)
    }

    class ViewHolder(val binding: ItemCharactersListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return charactersList.size
    }
}