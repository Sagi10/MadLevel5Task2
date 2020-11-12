package com.lalee.madlevel5task2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lalee.madlevel5task2.R
import com.lalee.madlevel5task2.model.Game
import com.lalee.madlevel5task2.ui.CHECKGAMEDELETION
import com.lalee.madlevel5task2.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(private var games: MutableList<Game>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    //variables for undo method
    private var removedPositon: Int = 0
    private lateinit var removedItem: Game
    private lateinit var allRemovedItems: List<Game>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun dataBind(game: Game) {
            itemView.tv_title.text = game.titel
            itemView.tv_platform.text = game.platform.toString()
            val dateRelease = String.format(
                "Release date: %s %s",
                game.releaseDate.toString().take(10),
                game.releaseDate.toString().takeLast(4)
            )
            itemView.tv_date.text = dateRelease
        }
    }

    fun removeAndUndoGame(viewHolder: RecyclerView.ViewHolder) {
        removedPositon = viewHolder.adapterPosition
        removedItem = games[viewHolder.adapterPosition]

        games.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
        CHECKGAMEDELETION = true


        Snackbar.make(viewHolder.itemView, "${removedItem.titel}, deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                games.add(removedPositon, removedItem)
                notifyItemInserted(removedPositon)
                CHECKGAMEDELETION = false
            }.show()
    }

    fun removeAllGamesAndUndo(viewHolder: RecyclerView.ViewHolder) {
        allRemovedItems = games // save the last list of games

        games = mutableListOf<Game>() //make a new list
        notifyDataSetChanged()

        Snackbar.make(viewHolder.itemView, "Backlog deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                games.addAll(allRemovedItems) // add the saved games list back to games list
                notifyDataSetChanged()
            }
    }
}