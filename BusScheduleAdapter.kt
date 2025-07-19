package com.example.bustract.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Responses.Model.History
import com.example.bustract.Responses.Model.Search
import com.example.bustract.databinding.ItemScheduleBinding
L
class BusScheduleAdapter(private val schedules: List<History>, val onclikc: (History) -> Unit) :
    RecyclerView.Adapter<BusScheduleAdapter.BusScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusScheduleViewHolder {
        val binding =
            ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusScheduleViewHolder, position: Int) {
        val schedule = schedules[position]
        holder.bind(schedule)
        holder.binding.root.setOnClickListener {
            onclikc(schedule)
        }
    }

    override fun getItemCount(): Int = schedules.size

    class BusScheduleViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: History) {
            binding.fromplace.text = schedule.fromplace
            binding.toplace.text = schedule.toplace
            binding.further.text =
                "Bus ${schedule.vehiclenumber} | ${schedule.starttime} - ${schedule.endtime}"
        }
    }
}
