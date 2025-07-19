package com.example.bustract.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Responses.Model.BusSchedule
import com.example.bustract.databinding.ItemScheduledBusBinding

class ScheduledBusAdapter(private var schedules: List<BusSchedule>) :
    RecyclerView.Adapter<ScheduledBusAdapter.BusScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusScheduleViewHolder {
        val view =
            ItemScheduledBusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusScheduleViewHolder, position: Int) {
        val schedule = schedules[position]
        holder.bind(schedule)
    }

    override fun getItemCount(): Int = schedules.size

    class BusScheduleViewHolder(private val binding: ItemScheduledBusBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: BusSchedule) {
            binding.tvVehicleNumber.text = schedule.vehiclenumber
            binding.tvRouteFrom.text = schedule.fromplace
            binding.tvRouteTo.text = schedule.toplace
            binding.tvTimes.text = "Arrival ${schedule.arrivaltime} - Departure${schedule.departuretime}"
            binding.tvPlatform.text = schedule.platformnumber
            binding.btnAction.setOnClickListener {

            }
        }
    }

    fun updateList(newList: List<BusSchedule>){
        schedules = newList
        notifyDataSetChanged()
    }
}

