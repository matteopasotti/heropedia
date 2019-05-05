package com.pasotti.matteo.wikiheroes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasotti.matteo.wikiheroes.models.DeskItem


@Dao
interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemInShop( item : DeskItem )


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemsInShop( items : List<DeskItem>)


    @Query("DELETE FROM DeskItem WHERE DeskItem.deskItemId = :id")
    fun removeItemFromShop( id : Int)

    @Query("SELECT * FROM DeskItem WHERE DeskItem.item_endYear == 0 AND DeskItem.item_startYear == 0 AND DeskItem.item_fullName is null")
    fun getComicsInDesk() : LiveData<List<DeskItem>>

    @Query("SELECT * FROM DeskItem WHERE DeskItem.item_endYear != 0 AND DeskItem.item_startYear != 0")
    fun getSeriesInDesk(): LiveData<List<DeskItem>>

    @Query("SELECT * FROM DeskItem WHERE DeskItem.deskItemId = :id")
    fun getItemFromShop( id : Int) : LiveData<DeskItem>
}