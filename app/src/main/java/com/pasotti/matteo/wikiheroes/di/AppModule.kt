package com.pasotti.matteo.wikiheroes.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.pasotti.matteo.wikiheroes.room.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    //Application instance is provided by BindInstance inside the Builder of the Compoonent
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "ComicCatalogDB.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: AppDatabase): ItemDao {
        return database.itemDao()
    }


    @Provides
    @Singleton
    fun provideComicsDao(database: AppDatabase): ComicsDao {
        return database.comicsDao()
    }

    @Provides
    @Singleton
    fun provideShopDao( database: AppDatabase) : ShopDao {
        return database.shopDao()
    }

    @Singleton
    @Provides
    fun providePreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}