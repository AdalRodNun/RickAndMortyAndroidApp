package com.myapp.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ItemLocationBinding
import com.myapp.rickandmorty.domain.model.LocationR

class LocationsPagingAdapter(
    private val onClickListener: (LocationR) -> Unit
) : PagingDataAdapter<LocationR, LocationsPagingAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position)?.let { character -> viewHolder.render(character, onClickListener) }
    }

    inner class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun render(location: LocationR, onClickListener: (LocationR) -> Unit) = with(binding) {
            ivIconType.setImageResource(location.typeIcon.icon)
            tvName.text = location.name
            tvDescription.text = itemView.context.getString(
                R.string.item_description_param,
                location.type,
                location.dimension
            )

            itemView.setOnClickListener {
                onClickListener(location)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<LocationR>() {
            override fun areItemsTheSame(oldItem: LocationR, newItem: LocationR): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LocationR, newItem: LocationR): Boolean {
                return oldItem == newItem
            }
        }
    }

}