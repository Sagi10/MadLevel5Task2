package com.lalee.madlevel5task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lalee.madlevel5task2.adapters.GameAdapter
import com.lalee.madlevel5task2.model.Game
import com.lalee.madlevel5task2.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    val allGames: LiveData<List<Game>> = gameRepository.getAllGames()

    val error = MutableLiveData<String>()
    val succes = MutableLiveData<Boolean>()

    fun insertGame(game: Game) {
        if (validateGameInput(game)){
            CoroutineScope(Dispatchers.IO).launch {
                gameRepository.insertGame(game)
            }
            succes.value = true
        }
    }


    fun deleteGame(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            gameRepository.deleteGame(id)
        }
    }

    fun deleteAllGames() {
        CoroutineScope(Dispatchers.IO).launch {
            gameRepository.deleteAllGames()
        }
    }

    private fun validateGameInput(game: Game): Boolean {
        return when {
            game.platform.toString().isEmpty() -> {
                error.value = "Platform must be chosen"
                false
            }
            game.titel.isBlank() || game.titel.isEmpty() -> {
                error.value = "Title must be filled in"
                false
            }
            else -> true
        }
    }
}