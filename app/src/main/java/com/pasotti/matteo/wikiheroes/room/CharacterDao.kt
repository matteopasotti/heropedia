package com.pasotti.matteo.wikiheroes.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.pasotti.matteo.wikiheroes.models.Character

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<Character>)

    @Update
    fun updateCharacters(characters: List<Character>)

    @Query("SELECT * FROM Character WHERE page = :page")
    fun getCharacters(page : Int): LiveData<List<Character>>

    @Query("SELECT * FROM Character WHERE name = :name")
    fun getCharacterByName(name: String) : Character

    @Update
    fun updateCharacter(character: Character)

    @Query("DELETE FROM Character WHERE id = :id")
    fun deleteCharacter(id: Int)


}