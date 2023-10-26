package com.example.assignmentone.database.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignmentone.database.dao.CharacterDao
import com.example.assignmentone.model.entity.Character

@Database(
    entities = [
        Character::class,
    ],
    version = AppDatabase.version,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val version: Int = 3
        private const val databaseName: String = "CHARACTER-DB"

        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }

    abstract fun getCharacterItemDAO(): CharacterDao
}