package com.example.assignmentone.di.module

import android.content.Context
import com.example.assignmentone.adapter.CharacterAdapter
import dagger.Module
import dagger.Provides


@Module
class AdapterModule {

//    @Provides
//    fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

    @Provides
    fun provideCharacterAdapter(context: Context) = CharacterAdapter(context)
}