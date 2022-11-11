package com.example.annoyingalarm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AlarmFragment : AppCompatActivity() {
    lateinit var alarmlog : ArrayList<AlarmItem>
    lateinit var alarmadapter : AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_alarm)

        val mainPageRv = findViewById<RecyclerView>(R.id.alarmTime)
        alarmlog = ArrayList()
        // alarmadapter = AlarmAdapter(alarmlog, this@AlarmFragment)
        mainPageRv.adapter = alarmadapter
        lifecycleScope.launch {
            (application as AlarmApplication).db.alarmDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    AlarmItem(
                        entity.set_time,
                        entity.set_day
                    )
                }.also { mappedList ->
                    alarmlog.clear()
                    alarmlog.addAll(mappedList)
                    alarmadapter.notifyDataSetChanged()
                }
            }
        }

        mainPageRv.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            mainPageRv.addItemDecoration(dividerItemDecoration)
        }

        findViewById<Button>(R.id.deleteAll).setOnClickListener {
            lifecycleScope.launch(IO) {
                (application as AlarmApplication).db.alarmDao().deleteAll()
            }
        }

        findViewById<Button>(R.id.record).setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }
    }

    fun delete(alarmItem: AlarmItem) {
        lifecycleScope.launch(IO) {
            (application as AlarmApplication).db.alarmDao().delete(
                AlarmEntity(
                    set_time = alarmItem.time,
                    set_day = alarmItem.days
                )
            )
        }
    }
}
