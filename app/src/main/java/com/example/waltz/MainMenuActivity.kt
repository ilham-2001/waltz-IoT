package com.example.waltz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.waltz.databinding.ActivityMainBinding
import com.example.waltz.databinding.ActivityMainMenuBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)

        val actionBar = supportActionBar
        actionBar!!.hide()

        setContentView(binding.root)

        changeFragement(MainMenuFragment())

        binding.bottomNav.setOnItemSelectedListener{
          when(it.itemId) {
              R.id.menu_home -> changeFragement(MainMenuFragment())
              R.id.menu_monitor -> changeFragement(MonitoringFragment())
              R.id.menu_setting -> changeFragement(SettingFragment())
          }
            true
        }
    }

    private fun changeFragement(fragment: Fragment) {
        val fm: FragmentManager = supportFragmentManager
        val fragTransaction: FragmentTransaction = fm.beginTransaction()
        fragTransaction.replace(R.id.main_frame, fragment)
        fragTransaction.commit()
    }

}