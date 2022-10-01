package com.example.waltz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StartingMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_starting_menu)
    }
}