package com.example.bustract.Adapters

import android.content.Context
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Interactions.Selector
import com.example.bustract.R
import com.example.bustract.Responses.Model.User
import java.util.Locale

class DepotAdapter(private val context: Context, private val depotList: List<User>,  val selector: Selector) :
    RecyclerView.Adapter<DepotAdapter.DepotViewHolder>() {

    class DepotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val depotName: TextView = itemView.findViewById(R.id.tv_depot_name)
        val depotEmail: TextView = itemView.findViewById(R.id.tv_email)
        val depotLocation: TextView = itemView.findViewById(R.id.tv_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_deport, parent, false)
        return DepotViewHolder(view)
    }

    override fun onBindViewHolder(holder: DepotViewHolder, position: Int) {
        val depot = depotList[position]

        holder.itemView.setOnClickListener {
            selector.state(depot)
        }

        holder.depotName.text = depot.name
        holder.depotEmail.text = depot.mail

        // Split coordinates and convert to readable address
        val coordinates = depot.location!!.split(", ")
        if (coordinates.size == 2) {
            try {
                val latitude = coordinates[0].toDouble()
                val longitude = coordinates[1].toDouble()
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    holder.depotLocation.text = addresses[0].getAddressLine(0)
                } else {
                    holder.depotLocation.text = depot.location // Fallback to raw coordinates
                }
            } catch (e: Exception) {
                holder.depotLocation.text = depot.location // Fallback to raw coordinates
                e.printStackTrace()
            }
        } else {
            holder.depotLocation.text = depot.location
        }
    }

    override fun getItemCount(): Int = depotList.size
}