package com.example.bustract.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Responses.Model.Route
import com.example.bustract.User.ViewTheLocation
import com.example.bustract.databinding.CardbindBinding

class ViewDoc(val context: Context, val data: ArrayList<Route>) :
    RecyclerView.Adapter<ViewDoc.MyView>() {
    class MyView(val card: CardbindBinding) : RecyclerView.ViewHolder(card.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyView(
        CardbindBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: MyView, position: Int) {
        val d = data[position]
        val k = "<b>From : </b>${d.fromplace}<br>" +
                "<b>To &nbsp&nbsp&nbsp&nbsp: </b>${d.toplace}<br>" +
                "<b>From LatLon: </b>${d.fromlatlon}<br>" +
                "<b>To &nbsp&nbsp&nbsp&nbsp LatLon: </b>${d.tolatlon}<br>"
        holder.card.details.text =
            HtmlCompat.fromHtml(k, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
        holder.itemView.setOnClickListener {
            if (d.latlon?.isNotEmpty() == true) {
                Intent(context, ViewTheLocation::class.java).apply {
                    putExtra("latlon", d.latlon)
                    context.startActivity(this)
                }
            } else {
                Toast.makeText(context, "No Bus Location", Toast.LENGTH_SHORT).show()
            }
        }

    }
}