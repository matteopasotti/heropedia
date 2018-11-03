package com.pasotti.matteo.wikiheroes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasotti.matteo.wikiheroes.models.Item


@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Item>)

    @Query("SELECT * FROM Item")
    fun getItems(): LiveData<List<Item>>

    @Query("SELECT * FROM Item WHERE type = :type")
    fun getItemsByType(type: String): LiveData<List<Item>>
}