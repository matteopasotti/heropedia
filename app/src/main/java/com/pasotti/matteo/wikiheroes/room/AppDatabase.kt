package com.pasotti.matteo.wikiheroes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pasotti.matteo.wikiheroes.models.Character

@Database(entities = [(Character::class)], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun characterDao() : CharacterDao
}