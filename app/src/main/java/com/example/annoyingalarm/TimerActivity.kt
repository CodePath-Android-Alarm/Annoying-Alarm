package com.example.annoyingalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.annoyingalarm.databinding.ActivityTimerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.roundToInt

class TimerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startStopButton.setOnClickListener{ startStopTimer()}
        binding.resetButton.setOnClickListener{ resetTimer() }

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId)
            {
//                R.id.matchingGame -> startActivity(Intent(this@TimerActivity,MatchingGameFragment::class.java))
                R.id.alarm -> startActivity(Intent(this@TimerActivity,MainActivity::class.java))
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.timer
    }

    private val updateTime: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.timeTV.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 8640000 / 360000
        val minutes = resultInt % 8640000 % 360000 / 6000
        val seconds = resultInt % 8640000 % 360000 % 6000 / 100
        val milliseconds = resultInt % 8640000 % 360000 % 6000 % 100

        return makeTimeString(hours, minutes, seconds, milliseconds)
    }

    private fun makeTimeString(hours: Int, minutes:Int, seconds:Int, milliseconds: Int): String = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds)

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        binding.timeTV.text = getTimeStringFromDouble(time)
    }

    private fun startStopTimer() {
        if (timerStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun stopTimer() {
        stopService(serviceIntent)
        binding.startStopButton.text = "Start"
        timerStarted = false
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        binding.startStopButton.text = "Stop"
        timerStarted = true
    }
}