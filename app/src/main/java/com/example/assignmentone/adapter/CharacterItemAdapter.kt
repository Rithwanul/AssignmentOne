package com.example.assignmentone.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.assignmentone.R
import com.example.assignmentone.databinding.LayoutCharacterItemBinding
import com.example.assignmentone.listener.CharacterItemClick
import com.example.assignmentone.model.entity.Character

class CharacterItemAdapter(private val context: Context): RecyclerView.Adapter<CharacterItemAdapter.ViewHolder>() {

    private var characters: List<Character> = ArrayList()

    private var characterItemClick: CharacterItemClick? = null


    class ViewHolder(private val binding: LayoutCharacterItemBinding, private val context: Context)
        : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(character: Character) {
            binding.image.load(character.image)
            binding.actor.text = character.actor
            binding.home.text = character.house
            binding.name.text = character.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DataBindingUtil.inflate<LayoutCharacterItemBinding>(
            LayoutInflater.from(context),
            R.layout.layout_character_item,
            null,
            false
        )

        binding.root.layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
        holder.itemView.setOnClickListener{
            characterItemClick?.onCLick(character)
        }
    }

    override fun getItemCount(): Int = characters.size

    fun setCharacters(characters: List<Character>) {
        this.characters = characters
    }

    fun setCharacterItemClickListener(characterItemClick: CharacterItemClick) {
        this.characterItemClick = characterItemClick
    }
}