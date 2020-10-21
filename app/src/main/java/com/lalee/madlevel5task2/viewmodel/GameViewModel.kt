package com.lalee.madlevel5task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lalee.madlevel5task2.model.Game
import com.lalee.madlevel5task2.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)

    val allGames : LiveData<List<Game>> = gameRepository.getAllGames()

    fun insertGame(game: Game) {
        CoroutineScope(Dispatchers.IO).launch {
            gameRepository.insertGame(game)
        }
    }
}