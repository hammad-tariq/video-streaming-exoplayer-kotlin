package com.intigral.androidassignment.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intigral.androidassignment.data.api.pojo.response.AwayTeam
import com.intigral.androidassignment.data.api.pojo.response.LineUp
import com.intigral.assignmenthammad.R
import kotlinx.android.synthetic.main.item_lineup.view.*

class ItemAwayLineUpAdapter(var context: Context, var lineUpAway: AwayTeam) : RecyclerView.Adapter<ItemAwayLineUpAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lineup,
                parent, false))
    }

    override fun getItemCount(): Int {

        return 5
//        return lineUp.Lineups.Data.HomeTeam.Players.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txtLineUpName.text = lineUpAway.Players.get(position).Name + " (" + lineUpAway.Players.get(position).Role + " )"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}