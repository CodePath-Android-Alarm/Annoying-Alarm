package com.example.annoyingalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Integer.valueOf
import java.util.*


class AddAlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_schedule)

        val calender = Calendar.getInstance()
        var i = Intent(applicationContext,AlarmReceiver::class.java)

        val set_time = findViewById<TimePicker>(R.id.fragment_createalarm_timePicker)
        // val set_recurring = findViewById<CheckBox>(R.id.alarm_recurring)

//        val monCheck = findViewById<CheckBox>(R.id.checkMon)
//        val tueCheck = findViewById<CheckBox>(R.id.checkTue)
//        val wedCheck = findViewById<CheckBox>(R.id.checkWed)
//        val thuCheck = findViewById<CheckBox>(R.id.checkThu)
//        val friCheck = findViewById<CheckBox>(R.id.checkFri)
//        val satCheck = findViewById<CheckBox>(R.id.checkSat)
//        val sunCheck = findViewById<CheckBox>(R.id.checkSun)
//
//        val dayList = listOf<CheckBox>(monCheck,tueCheck,wedCheck,thuCheck,friCheck,satCheck,sunCheck)

//        dayList.forEach{it.isEnabled = set_recurring.isChecked}
//
//        set_recurring.setOnClickListener{
//            if (!set_recurring.isChecked)
//            {
//                dayList.forEach{
//                    it.isEnabled = false
//                    it.isChecked = false
//                }
//            } else
//            {
//                dayList.forEach{
//                    it.isEnabled = true
//                }
//            }
//        }
        findViewById<Button>(R.id.scheduleAlarm_Button).setOnClickListener {
            var days: String = ""
//            if(set_recurring.isChecked)
//            {
//                dayList.forEach {
//                    if (it.isChecked) days += " ${it.text}"
//                }
//            } else { days = "SOON 😈"}

            days = "SOON 😈"
            var hour = set_time.hour
            var minute = set_time.minute

            var isPM = hour >= 12
            var time = String.format("%2d:%02d %s",
            if (hour == 12 || hour == 0) 12 else hour%12,
            minute,
            if (isPM) "PM" else "AM"
            )

            set_time.setIs24HourView(false)

            var alarmItem = AlarmItem(time, days)

            lifecycleScope.launch(Dispatchers.IO) {
                (application as AlarmApplication).db.alarmDao().insert(
                    AlarmEntity(
                        set_time = alarmItem.time,
                        set_day = alarmItem.days
                    )
                )
            }

            //play alarm at specific time
            calender.set(Calendar.HOUR_OF_DAY,set_time.hour)
            calender.set(Calendar.MINUTE,set_time.minute)

            var hour_string = valueOf(hour).toString()
            var minute_string = valueOf(minute).toString()

            if(hour > 12) hour_string = valueOf(hour - 12).toString()
            if(minute < 10) minute_string =  "0"+ valueOf(minute).toString()

            var pi = PendingIntent.getBroadcast(applicationContext,111,i,PendingIntent.FLAG_IMMUTABLE)
            var am : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calender.timeInMillis,pi)

            finish()
        }
    }
}