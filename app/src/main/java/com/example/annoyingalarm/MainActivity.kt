package com.example.annoyingalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val matchingGameFragment: Fragment = MatchingGameFragment()
        val alarmFragment: Fragment = AlarmFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item->
            lateinit var fragment: Fragment
            when(item.itemId)
            {
                R.id.matchingGame -> fragment = matchingGameFragment
                R.id.alarm -> fragment = alarmFragment
            }
            replaceFragment(fragment)
            true
        }
        bottomNavigationView.selectedItemId = R.id.alarm
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.alarm_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}