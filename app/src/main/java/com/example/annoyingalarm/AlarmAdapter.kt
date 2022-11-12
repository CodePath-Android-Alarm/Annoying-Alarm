package com.example.annoyingalarm

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

lateinit var application: Application

class AlarmAdapter(private val alarmLog : ArrayList<AlarmItem>)
    : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val alarm = itemView.findViewById<TextView>(R.id.alarm_display_time)
        val alarmRepeat = itemView.findViewById<TextView>(R.id.alarm_display_day)

//        fun bind(alarmItem: AlarmItem) {
//            alarm.text = alarmItem.time
//            alarmRepeat.text = alarmItem.days
//        }
    }

    fun removeAt(position: Int) {
        val alarmItem = alarmLog[position]
        alarmLog.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, alarmLog.size)
        // activity.delete(alarmItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.alarm_display, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val log = alarmLog[position]

        holder.alarm.text = log.time.toString()
        holder.alarmRepeat.text = log.days.toString()
        // holder.bind(log)
    }

    override fun getItemCount() = alarmLog.size
}
