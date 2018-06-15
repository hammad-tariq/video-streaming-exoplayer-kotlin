package com.intigral.assignmenthammad.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intigral.assignmenthammad.data.api.pojo.response.HomeTeam
import com.intigral.assignmenthammad.data.api.pojo.response.LineUp
import com.intigral.assignmenthammad.R
import kotlinx.android.synthetic.main.item_lineup.view.*

class ItemHomeLineUpAdapter(var context: Context, var lineUpHome: HomeTeam) : RecyclerView.Adapter<ItemHomeLineUpAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lineup,
                parent, false))
    }

    override fun getItemCount(): Int {

        return 5
//        return lineUp.Lineups.Data.HomeTeam.Players.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txtLineUpName.text = lineUpHome.Players.get(position).Name + " (" + lineUpHome.Players.get(position).Role + " )"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}