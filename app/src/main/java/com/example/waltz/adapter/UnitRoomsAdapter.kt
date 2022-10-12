package com.example.waltz.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.waltz.R
import com.example.waltz.data.UnitRoom
import com.example.waltzLib.MQTTClient
import com.google.android.material.switchmaterial.SwitchMaterial

class UnitRoomsAdapter(
    private val unitRoomsList: ArrayList<UnitRoom>,
    private val context: Context,
    private val mqttClient: MQTTClient
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
        holder.unitAcCount.text = "${unitRoomsList[position].acCount} Unit"

        // Set darker background color when switch is toggled off
        if (!unitRoomsList[position].acSwitchStatus) {
            holder.cvHolder.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.not_active
                )
            )
        } else {
            holder.acSwitch.isChecked = true
        }

        holder.acSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("Adapter", isChecked.toString())
            if (isChecked) {
                mqttClient.publish("waltz/power${unitRoomsList[position].roomOrigin}", "ON")
                holder.cvHolder.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.active
                    )
                )
            } else {
                Log.d("Adapter", "ON")
                mqttClient.publish("waltz/power/${unitRoomsList[position].roomOrigin}", "OFF")
                holder.cvHolder.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.not_active
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return unitRoomsList.size
    }

    inner class UnitRoomsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val unitRoom: TextView = itemView.findViewById(R.id.unitTextRoom)
        val unitRoomPowerUsage: TextView = itemView.findViewById(R.id.unitPowerUsage)
        val unitAcCount: TextView = itemView.findViewById(R.id.countUnitRoom)
        val cvHolder: CardView = itemView.findViewById(R.id.cardAcUnit)
        val acSwitch: SwitchMaterial = itemView.findViewById(R.id.switchUnit)
    }

}