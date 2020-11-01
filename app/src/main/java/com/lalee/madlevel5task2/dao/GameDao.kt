package com.lalee.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lalee.madlevel5task2.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM Game WHERE id = :id")
    suspend fun deleteGame(id: Long)

    @Query("SELECT * FROM Game ORDER BY releaseDate ASC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("DELETE FROM Game")
    suspend fun deleteAllGames()
}