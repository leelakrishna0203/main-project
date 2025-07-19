package com.example.bustract.Adapters

import android.content.Context
import android.content.Intent
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Responses.Model.History
import com.example.bustract.User.ViewFullOrder
import com.example.bustract.databinding.CardbindBinding

class HistoryView(val context: Context, val data: ArrayList<History>) :
    RecyclerView.Adapter<HistoryView.MyView>() {
    class MyView(val item: CardbindBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyView(
        CardbindBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val k = data[position]
            val text = "<b>From : </b>${k.fromplace}<br>" +
                    "<b>To : </b>${k.toplace}<br>" +
                    "<b>Booked Date : </b>${k.datepayed}<br>" +
                    "<b>Total Paid : </b>₹${k.total} /-<br>" +
                    "<b>Bus Number : </b>${k.vehiclenumber}"
        holder.item.details.apply {
            this.text=spanable(text)
            setOnClickListener {
context.startActivity(Intent(context,ViewFullOrder::class.java).apply {
    putExtra("data",k)
})
            }
        }


    }

    fun spanable(string: String): Spanned {
        return HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)

    }
}