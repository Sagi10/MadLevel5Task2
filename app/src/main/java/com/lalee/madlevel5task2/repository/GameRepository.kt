package com.lalee.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.lalee.madlevel5task2.dao.GameDao
import com.lalee.madlevel5task2.database.GameDatabase
import com.lalee.madlevel5task2.model.Game
import com.lalee.madlevel5task2.model.Platform

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun insertGame(game: Game) {
        return gameDao.insertGame(game)
    }

    suspend fun deleteGame(id: Long){
        return gameDao.deleteGame(id)
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }

    suspend fun deleteAllGames(){
        gameDao.deleteAllGames()
    }
}