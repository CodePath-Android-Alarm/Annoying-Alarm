package com.example.annoyingalarm
import android.os.Build
import android.widget.TimePicker
import java.util.*

class TimePickerUtil {
    fun getTimePickerHour(): Int {
        val time = Calendar.getInstance()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            time.get(Calendar.HOUR)
        } else {
            time.get(Calendar.HOUR_OF_DAY)
        }
    }

    fun getTimePickerMinute(): Int {
        val time = Calendar.getInstance()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            time.get(Calendar.MINUTE)
        } else {
            time.get(Calendar.HOUR_OF_DAY)
        }
    }
}