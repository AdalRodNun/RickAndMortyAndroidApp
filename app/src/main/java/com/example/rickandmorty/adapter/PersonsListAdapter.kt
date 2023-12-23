package com.example.rickandmorty.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemPersonsListBinding
import com.example.rickandmorty.room.models.Person

class PersonsListAdapter(
    private var personsList: List<Person>,
    private val onClickLister: (Person) -> Unit
) : RecyclerView.Adapter<PersonsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPersonsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personItem = personsList[position]

        holder.binding.nameTxt.text = personItem.name
        holder.binding.cameraButton.setImageBitmap(stringToBitmap(personItem.photo))
        holder.binding.ageTxt.text =
            if (personItem.age > 1) "${personItem.age} years" else "${personItem.age} year"

        holder.binding.cardView.setOnClickListener {
            onClickLister(personItem)
        }
    }

    class ViewHolder(val binding: ItemPersonsListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return personsList.size
    }

    private fun stringToBitmap(compressedString: String): Bitmap {
        val codedString: ByteArray = Base64.decode(compressedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(codedString, 0, codedString.size)
    }
}