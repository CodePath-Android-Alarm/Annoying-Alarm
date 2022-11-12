package com.example.annoyingalarm

import kotlinx.serialization.Serializable

@Serializable
class AlarmItem(
    val time : String?,
    val days : String?
    ) : java.io.Serializable