package com.lalee.madlevel5task2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lalee.madlevel5task2.R
import com.lalee.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameAdapter(private var games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun dataBind(game: Game){
            itemView.tv_title.text = game.titel
            itemView.tv_platform.text = game.platform.toString()
            itemView.tv_date.text = game.releaseDate.toString()
        }
    }
}