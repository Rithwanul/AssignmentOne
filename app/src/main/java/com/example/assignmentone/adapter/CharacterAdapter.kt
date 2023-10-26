package com.example.assignmentone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.assignmentone.R
import com.example.assignmentone.databinding.LayoutCharacterItemBinding
import com.example.assignmentone.listener.CharacterItemClick
import com.example.assignmentone.model.entity.Character
import timber.log.Timber
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    private val context: Context
) : PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(Comparator) {


    private var characterItemClick: CharacterItemClick? = null


    class CharacterViewHolder constructor(
        private val binding: LayoutCharacterItemBinding
        ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character?) {
            binding.image.load(character?.image)
            Timber.tag("log").e(character?.image.toString())
            binding.actor.text = character?.actor
            binding.home.text = character?.house
            binding.name.text = character?.name
        }

    }

    fun setCharacterItemClickListener(characterItemClick: CharacterItemClick) {
        this.characterItemClick = characterItemClick
    }

    object Comparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.itemView.setOnClickListener {
            characterItemClick?.onCLick(character!!)
        }
        holder.bind(character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
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

        return CharacterViewHolder(binding)
    }
}