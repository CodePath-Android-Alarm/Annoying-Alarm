package com.example.annoyingalarm

import android.app.TimePickerDialog.OnTimeSetListener
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Console


class AddAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_schedule)

        val set_time = findViewById<TimePicker>(R.id.fragment_createalarm_timePicker)
        val set_recurring = findViewById<CheckBox>(R.id.alarm_recurring)

        val monCheck = findViewById<CheckBox>(R.id.checkMon)
        val tueCheck = findViewById<CheckBox>(R.id.checkTue)
        val wedCheck = findViewById<CheckBox>(R.id.checkWed)
        val thuCheck = findViewById<CheckBox>(R.id.checkThu)
        val friCheck = findViewById<CheckBox>(R.id.checkFri)
        val satCheck = findViewById<CheckBox>(R.id.checkSat)
        val sunCheck = findViewById<CheckBox>(R.id.checkSun)

        val dayList = listOf<CheckBox>(monCheck,tueCheck,wedCheck,thuCheck,friCheck,satCheck,sunCheck)

        dayList.forEach{it.isEnabled = set_recurring.isChecked}

        set_recurring.setOnClickListener{
            if (!set_recurring.isChecked)
            {
                dayList.forEach{
                    it.isEnabled = false
                    it.isChecked = false
                }
            } else
            {
                dayList.forEach{
                    it.isEnabled = true
                }
            }
        }
        findViewById<Button>(R.id.scheduleAlarm_Button).setOnClickListener {
            var days: String = ""
            if(set_recurring.isChecked)
            {
                dayList.forEach {
                    if (it.isChecked) days += " ${it.text}"
                }
            } else { days = "SOON 😈"}

            val hour = set_time.hour
            val minute = set_time.minute

            val isPM = hour >= 12
            val time = String.format("%2d:%02d %s",
            if (hour == 12 || hour == 0) 12 else hour%12,
            minute,
            if (isPM) "PM" else "AM"
            )

            set_time.setIs24HourView(false)

            val alarmItem = AlarmItem(time, days)

            lifecycleScope.launch(Dispatchers.IO) {
                (application as AlarmApplication).db.alarmDao().insert(
                    AlarmEntity(
                        set_time = alarmItem.time,
                        set_day = alarmItem.days
                    )
                )
            }

            finish()
        }
    }
}