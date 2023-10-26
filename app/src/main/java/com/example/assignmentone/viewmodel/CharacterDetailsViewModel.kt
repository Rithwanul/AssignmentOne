package com.example.assignmentone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.assignmentone.database.config.AppDatabase
import com.example.assignmentone.service.NetworkService
import com.google.gson.Gson
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(

    private val networkService: NetworkService,
    private val gson: Gson,
    private val appDatabase: AppDatabase
) : ViewModel(){


//    fun getAllCharacter(id: String) = flow {
//        val allCharacters = networkService.getAllCharacters()
//        emit(networkService.getCharacterDetailsById(id))
//    }

    suspend fun getAllCharacter(id: String) = networkService.getCharacterDetailsById(id)
}