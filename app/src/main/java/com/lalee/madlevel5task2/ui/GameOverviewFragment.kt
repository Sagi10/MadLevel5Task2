package com.lalee.madlevel5task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lalee.madlevel5task2.R
import com.lalee.madlevel5task2.adapters.GameAdapter
import com.lalee.madlevel5task2.model.Game
import com.lalee.madlevel5task2.model.Platform
import com.lalee.madlevel5task2.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_overview.*
import kotlinx.android.synthetic.main.item_game.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameOverviewFragment : Fragment() {

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    private val gameViewModel : GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val gameList = generateDummyList(20)

        initRV()
        observerAddGameResult()
    }


    private fun initRV() {
        rv_games.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_games.adapter = gameAdapter
        createItemTouchHelper().attachToRecyclerView(rv_games)
    }

    private fun observerAddGameResult(){
        gameViewModel.allGames.observe(viewLifecycleOwner, Observer {
            this@GameOverviewFragment.games.clear()
            this@GameOverviewFragment.games.addAll(it)
            gameAdapter.notifyDataSetChanged()
        })
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //TODO
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun generateDummyList(size: Int): List<Game> {
        val list = ArrayList<Game>()
        for (i in 0 until size) {
            val item = Game("Title $i", Platform.XBOX, Date())
            list += item
        }
        return list
    }
}
