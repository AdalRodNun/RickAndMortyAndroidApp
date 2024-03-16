package com.myapp.rickandmorty.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ItemCharacterBinding
import com.myapp.rickandmorty.domain.model.CharacterR

class CharactersPagingAdapter(
    private val onClickListener: (CharacterR) -> Unit
) : PagingDataAdapter<CharacterR, CharactersPagingAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position)?.let { character -> viewHolder.render(character, onClickListener) }
    }

    inner class ViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun render(character: CharacterR, onClickListener: (CharacterR) -> Unit) = with(binding) {
            Glide.with(itemView.context).load(character.image).into(ivCharacter)
            tvName.text = character.name
            tvDescription.text = itemView.context.getString(
                R.string.item_description_param,
                character.status.str,
                character.species
            )

            ivStatus.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(itemView.context, character.status.color)
            )

            itemView.setOnClickListener {
                onClickListener(character)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<CharacterR>() {
            override fun areItemsTheSame(oldItem: CharacterR, newItem: CharacterR): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterR, newItem: CharacterR): Boolean {
                return oldItem == newItem
            }
        }
    }

}