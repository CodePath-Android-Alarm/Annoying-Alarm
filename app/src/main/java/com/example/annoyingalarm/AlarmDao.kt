package com.example.annoyingalarm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm_table")
    fun getAll(): Flow<List<AlarmEntity>>

    @Insert
    fun insert(alarmEntity: AlarmEntity)

    @Query("DELETE FROM alarm_table")
    fun deleteAll()

    @Delete
    fun delete(alarmEntity: AlarmEntity)
}