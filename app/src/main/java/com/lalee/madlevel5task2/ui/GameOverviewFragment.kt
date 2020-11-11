package com.lalee.madlevel5task2.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
import kotlinx.android.synthetic.main.item_fab.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

var CHECKGAMEDELETION = false

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
public class GameOverviewFragment : Fragment() {

    private val games = arrayListOf<Game>()
    val gameAdapter = GameAdapter(games)

    private val gameViewModel: GameViewModel by viewModels()

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

    private fun observerAddGameResult() {
        gameViewModel.allGames.observe(viewLifecycleOwner, {
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
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]

                gameAdapter.removeAndUndoGame(viewHolder)

                Timer().schedule(timerTask {
                    if (CHECKGAMEDELETION) {
                        gameToDelete.id?.let { gameViewModel.deleteGame(it) }
                    }
                }, 4500)
                gameAdapter.notifyDataSetChanged()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val backgroundColor = ColorDrawable(Color.RED)

                if (dX > 0) {
                    //do something because right is swiped
                } else {
                    // swiped left
                    backgroundColor.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                }
                backgroundColor.draw(c)

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
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
