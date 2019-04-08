package com.pasotti.matteo.wikiheroes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasotti.matteo.wikiheroes.models.Detail


@Dao
interface ComicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic( comic : Detail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComics( comics : List<Detail>)

    @Query("SELECT * FROM Detail WHERE page = :page")
    fun getComicsByPage( page : Int) : LiveData<List<Detail>>

    @Query("SELECT * FROM Detail ORDER BY page")
    fun getComics() : LiveData<List<Detail>>

    @Query("SELECT * FROM Detail WHERE title = :title")
    fun getComicByTitle( title : String) : LiveData<Detail>
}