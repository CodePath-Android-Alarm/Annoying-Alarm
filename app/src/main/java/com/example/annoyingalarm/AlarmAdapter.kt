package com.example.annoyingalarm

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

lateinit var application: Application

class AlarmAdapter(private val alarmlog : ArrayList<AlarmItem>, private val activity: AlarmFragment)
    : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val alarm = itemView.findViewById<TextView>(R.id.alarmtime)
        private val alarmrepeat = itemView.findViewById<TextView>(R.id.alarmday)

        fun bind(alarmItem: AlarmItem) {
            alarm.text = alarmItem.time
            alarmrepeat.text = alarmItem.days
        }
    }

    fun removeAt(position: Int) {
        val alarmItem = alarmlog[position]
        alarmlog.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, alarmlog.size)
        activity.delete(alarmItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_display, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val log = alarmlog[position]
        holder.bind(log)
    }

    override fun getItemCount() = alarmlog.size
}
