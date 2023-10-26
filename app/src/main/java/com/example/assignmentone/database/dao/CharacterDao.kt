package com.example.assignmentone.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmentone.model.entity.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterItem: Character)

    @Query("SELECT * from tbl_character")
    fun getCharacters(): List<Character>

    @Query("Select count(*) from tbl_character")
    fun isCharacterAvailable(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("SELECT * from tbl_character")
    fun getCharactersBYPage(): PagingSource<Int, Character>
}