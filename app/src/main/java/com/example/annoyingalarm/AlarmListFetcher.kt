package com.example.annoyingalarm

class AlarmListFetcher {
    companion object{
        var alarmList: MutableList<AlarmItem> = ArrayList()

        fun GetAlarmList():MutableList<AlarmItem> {
            return alarmList
        }
    }
}