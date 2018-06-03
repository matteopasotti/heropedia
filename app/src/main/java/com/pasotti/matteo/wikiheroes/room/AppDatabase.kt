package com.pasotti.matteo.wikiheroes.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.pasotti.matteo.wikiheroes.models.Character

@Database(entities = [(Character::class)], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun characterDao() : CharacterDao
}