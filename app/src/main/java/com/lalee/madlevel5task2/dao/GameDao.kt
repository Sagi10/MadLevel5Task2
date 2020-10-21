package com.lalee.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lalee.madlevel5task2.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * from Game")
    fun getAllGames(): LiveData<List<Game>>
}