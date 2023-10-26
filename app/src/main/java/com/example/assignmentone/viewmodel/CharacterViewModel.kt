package com.example.assignmentone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignmentone.database.config.AppDatabase
import com.example.assignmentone.remotemediator.CharacterRemoteMediator
import com.example.assignmentone.service.NetworkService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.assignmentone.model.entity.Character

class CharacterViewModel @Inject constructor(

    private val networkService: NetworkService,
    private val gson: Gson,
    private val appDatabase: AppDatabase
) : ViewModel(){

    fun getAllCharacter() = flow {
        val allCharacters = networkService.getAllCharacters()
        emit(allCharacters)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getAllCharacters(): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 100
        ),
        pagingSourceFactory = {
            appDatabase.getCharacterItemDAO().getCharactersBYPage()
        },
        remoteMediator = CharacterRemoteMediator(appDatabase, networkService)
    ).flow
}