package com.example.bustract.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bustract.Interactions.Selector
import com.example.bustract.Responses.Model.User
import com.example.bustract.databinding.DrviercardBinding

class DriverView(
    var context: Context,
    var data: ArrayList<User>,
    val selector: Selector
) : RecyclerView.Adapter<DriverView.Viewing>() {
    class Viewing(val item: DrviercardBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Viewing(
        DrviercardBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
    )

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: Viewing, position: Int) {
        val k = data[position]
        with(holder.item) {
            image.load(k.profile)
            val text = "<b>Driver name </b>: ${k.name}<br>" +
                    "<b>Mobile Number</b>: ${k.mobile}<br>" +
                    "<b>License Number</b>: ${k.drivinglicense}"
            details2.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
        }
        holder.itemView.setOnClickListener {
            selector.state(k)
        }
    }
}