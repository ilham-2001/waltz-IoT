package com.example.waltz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waltz.R
import com.example.waltz.data.UnitRoom

class UnitRoomsAdapter(
    private val unitRoomsList: ArrayList<UnitRoom>,
    private val context: Context
) : RecyclerView.Adapter<UnitRoomsAdapter.UnitRoomsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UnitRoomsAdapter.UnitRoomsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.ac_unit_item,
            parent,
            false
        )

        return UnitRoomsHolder(itemView)
    }

    override fun onBindViewHolder(holder: UnitRoomsHolder, position: Int) {
        holder.unitRoom.text = unitRoomsList[position].roomOrigin
        holder.unitRoomPowerUsage.text = unitRoomsList[position].totalPowerUsage.toString()
        holder.unitAcCount.text = unitRoomsList[position].acCount.toString() + " Unit"
    }

    override fun getItemCount(): Int {
        return  unitRoomsList.size
    }

    class UnitRoomsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val unitRoom: TextView = itemView.findViewById(R.id.unitTextRoom)
        val unitRoomPowerUsage: TextView = itemView.findViewById(R.id.unitPowerUsage)
        val unitAcCount: TextView = itemView.findViewById(R.id.countUnitRoom)
    }

}