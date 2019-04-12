package com.pasotti.matteo.wikiheroes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.models.ShopItem

@Database(entities = [(Character::class), (Item::class) , (Detail::class) , (ShopItem::class) ], version = 7)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun characterDao() : CharacterDao

    abstract fun itemDao() : ItemDao

    abstract fun comicsDao() : ComicsDao

    abstract fun shopDao() : ShopDao
}