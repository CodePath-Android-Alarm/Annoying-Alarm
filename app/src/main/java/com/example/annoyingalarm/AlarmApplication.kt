package com.example.annoyingalarm

import android.app.Application

class AlarmApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}