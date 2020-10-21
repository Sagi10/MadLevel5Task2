package com.lalee.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.lalee.madlevel5task2.dao.GameDao
import com.lalee.madlevel5task2.database.GameDatabase
import com.lalee.madlevel5task2.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun insertGame(game: Game) {
        return gameDao.insertGame(game)
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }
}