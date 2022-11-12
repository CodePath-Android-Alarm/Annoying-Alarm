package com.example.annoyingalarm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var alarmlog : ArrayList<AlarmItem>
    lateinit var alarmadapter : AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainPageRv = findViewById<RecyclerView>(R.id.alarmTime)
        alarmlog = ArrayList()
        alarmadapter = AlarmAdapter(alarmlog)
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
            lifecycleScope.launch(Dispatchers.IO) {
                (application as AlarmApplication).db.alarmDao().deleteAll()
            }
        }

        findViewById<Button>(R.id.record).setOnClickListener {
            val intent = Intent(this, AddAlarmActivity::class.java)
            startActivity(intent)
        }


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId)
            {
                R.id.matchingGame -> startActivity(Intent(this@MainActivity,MatchingGameFragment::class.java))
                R.id.timer -> startActivity(Intent(this@MainActivity,TimerActivity::class.java ))
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.alarm
    }

    fun delete(alarmItem: AlarmItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            (application as AlarmApplication).db.alarmDao().delete(
                AlarmEntity(
                    set_time = alarmItem.time,
                    set_day = alarmItem.days
                )
            )
        }
    }
}