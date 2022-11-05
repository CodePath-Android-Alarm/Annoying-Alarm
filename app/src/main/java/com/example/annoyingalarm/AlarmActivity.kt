package com.example.annoyingalarm

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_schedule)

        findViewById<Button>(R.id.scheduleAlarm_Button).setOnClickListener {
            val set_time = findViewById<TimePicker>(R.id.fragment_createalarm_timePicker)
            val set_day = findViewById<CheckBox>(R.id.alarm_recurring)

            val alarmItem = AlarmItem(set_time.toString(), set_day.text.toString())

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