package com.example.assignmentone.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.assignmentone.R
import com.example.assignmentone.databinding.FragmentCharacterDetailsBinding
import com.example.assignmentone.di.factory.ViewModelFactory
import com.example.assignmentone.viewmodel.CharacterDetailsViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterDetailsFragment @Inject constructor(
    private val factory: ViewModelFactory,
    private val gson: Gson
) : Fragment() {


    private lateinit var binding: FragmentCharacterDetailsBinding
    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_character_details, container, false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("data")


        viewModel = ViewModelProvider(this, factory)[CharacterDetailsViewModel::class.java]

        CoroutineScope(Dispatchers.IO).launch {
            if (id != null) {
                val character = viewModel.getAllCharacter(id)[0]

                binding.name.text = "Name : ${character.name}"
                binding.house.text = "House : ${character.house}"
                binding.species.text = "Species : ${character.species}"
                binding.actor.text = "Actor : ${character.actor}"
                binding.gender.text = "Gender : ${character.gender}"
                binding.ancestry.text = "Anchestry : ${character.ancestry}"
            }
        }
    }
}