package com.pasotti.matteo.wikiheroes.di

import androidx.room.Room
import com.pasotti.matteo.wikiheroes.room.AppDatabase
import org.koin.dsl.module.applicationContext

val dbModule = applicationContext {

    bean { Room.databaseBuilder(get(), AppDatabase::class.java, "ComicCatalogDB.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build() }

    bean { get<AppDatabase>().characterDao() }

    bean { get<AppDatabase>().itemDao() }

    bean { get<AppDatabase>().comicsDao() }

    bean { get<AppDatabase>().favCharacterDao() }

    bean { get<AppDatabase>().shopDao() }

}