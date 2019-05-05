package com.pasotti.matteo.wikiheroes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pasotti.matteo.wikiheroes.models.*

@Database(entities = [(Character::class), (Item::class) , (Detail::class) , (DeskItem::class) , (FavCharacter::class) ], version = 9)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun characterDao() : CharacterDao

    abstract fun itemDao() : ItemDao

    abstract fun comicsDao() : ComicsDao

    abstract fun shopDao() : ShopDao

    abstract fun favCharacterDao() : FavCharacterDao
}