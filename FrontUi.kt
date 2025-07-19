package com.example.bustract.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Admin.ViewDrivers
import com.example.bustract.Responses.Model.Route
import com.example.bustract.databinding.CardbindBinding

class FrontUi(val context: Context
,val array:ArrayList<Route>):RecyclerView.Adapter<FrontUi.Viewed>() {
    class Viewed(val item :CardbindBinding):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=Viewed(
        CardbindBinding.inflate(LayoutInflater.from(context), parent,false))

    override fun getItemCount()=array.size

    override fun onBindViewHolder(holder: Viewed, position: Int) {
        val d=array[position]
        with(holder.item){
      val html="<b>From : </b>${d.fromplace}<br>" +
               "<b>To &nbsp&nbsp&nbsp&nbsp: </b>${d.toplace}<br>" +
              "<b>From LatLon: </b>${d.fromlatlon}<br>" +
              "<b>To &nbsp&nbsp&nbsp&nbsp LatLon: </b>${d.tolatlon}<br>"
      details.text=HtmlCompat.fromHtml(html,HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
  }
        holder.itemView.setOnClickListener {
            Intent(context,ViewDrivers::class.java).apply {
            putExtra("data1",d)
                context.startActivity(this)
            }

        }
    }
}
