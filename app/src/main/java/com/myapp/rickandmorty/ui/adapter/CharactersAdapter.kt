package com.myapp.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapp.rickandmorty.databinding.ItemCharacterBinding
import com.myapp.rickandmorty.domain.model.CharacterR

class CharactersAdapter(
    private val charactersDataSet: MutableList<CharacterR>,
    private val onClickListener: (CharacterR) -> Unit
) :
    RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val character = charactersDataSet[position]
        viewHolder.render(character, onClickListener)
    }

    override fun getItemCount() = charactersDataSet.size

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun render(character: CharacterR, onClickListener: (CharacterR) -> Unit) = with(binding) {
            tvName.text = character.name
            tvDescription.text = "${character.species} - ${character.status}"
            Glide.with(ivCharacter.context).load(character.image).into(ivCharacter)

            itemView.setOnClickListener {
                onClickListener(character)
            }
        }
    }

}