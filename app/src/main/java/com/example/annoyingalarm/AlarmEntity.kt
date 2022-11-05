package com.example.annoyingalarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "Alarm_Time") val set_time: String?,
    @ColumnInfo(name = "Alarm_Day") val set_day: String?
)
