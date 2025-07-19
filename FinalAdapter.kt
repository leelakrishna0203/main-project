package com.example.bustract.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Responses.Model.Custome
import com.example.bustract.databinding.CardbindBinding

class FinalAdapter(val context: Context,val data:ArrayList<Custome>):RecyclerView.Adapter<FinalAdapter.OwnView>() {
    class OwnView(val item :CardbindBinding):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=OwnView(
        CardbindBinding.inflate(LayoutInflater.from(context),
            parent,false)
    )

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: OwnView, position: Int) {
   data[position].apply {

       val text="<b>Name :</b>$name<br>" +
               "<b>Mail :</b>$mail<br>" +
               "<b>Mobile :</b>$mobile<br>" +
               "<b>From Place:</b>$fromplace<br>" +
               "<b>To Place:</b>$toplace<br>" +
               "<b>Payment :</b>$payment<br>" +
               "<b>Booking date:</b>$datepayed<br> " +
               "<b>Seats:</b>$seats"
       holder.item.details.text=HtmlCompat.fromHtml(text,HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
   }
        /*payment
        seats
        datepayed
        total
        name
        mobile
        mail
        fromplace
        toplace
        fromlatlon
        tolatlon
        driverid
        latlon*/


    }
}