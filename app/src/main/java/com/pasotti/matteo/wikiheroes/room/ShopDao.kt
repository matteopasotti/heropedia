package com.pasotti.matteo.wikiheroes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasotti.matteo.wikiheroes.models.ShopItem


@Dao
interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemInShop( item : ShopItem )


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemsInShop( items : List<ShopItem>)


    @Query("DELETE FROM ShopItem WHERE ShopItem.shopItemId = :id")
    fun removeItemFromShop( id : Int)

    @Query("SELECT * FROM ShopItem")
    fun getItemsInShop() : LiveData<List<ShopItem>>

    @Query("SELECT * FROM ShopItem WHERE ShopItem.shopItemId = :id")
    fun getItemFromShop( id : Int) : LiveData<ShopItem>
}