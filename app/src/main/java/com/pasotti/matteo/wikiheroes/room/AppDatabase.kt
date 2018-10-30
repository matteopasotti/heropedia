package com.pasotti.matteo.wikiheroes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Item

@Database(entities = [(Character::class), (Item::class)], version = 2)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun characterDao() : CharacterDao

    abstract fun itemDao() : ItemDao
}