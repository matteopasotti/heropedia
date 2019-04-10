package com.pasotti.matteo.wikiheroes.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.utils.Utils


@Dao
interface ComicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic( comic : Detail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComics( comics : List<Detail>)

    @Query("SELECT * FROM Detail WHERE page = :page AND week = :week")
    fun getComicsByPage( page : Int , week : Utils.WEEK) : LiveData<List<Detail>>

    @Query("SELECT * FROM Detail WHERE week = :week ORDER BY page")
    fun getComics( week : Utils.WEEK) : LiveData<List<Detail>>

    @Query("SELECT * FROM Detail WHERE title = :title")
    fun getComicByTitle( title : String) : LiveData<Detail>

    @Query("DELETE FROM Detail")
    fun deleteComics()
}