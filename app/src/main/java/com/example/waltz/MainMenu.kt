package com.example.waltz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.cardview.widget.CardView

class MainMenu : AppCompatActivity() {

    private lateinit var cvDashboard: CardView
    private lateinit var cvPayment: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar!!.hide()

        setContentView(R.layout.activity_main_menu)

        findComponent()

        cvDashboard.setOnClickListener {
            val dashBoard = Intent(this, DashBoard::class.java)
            startActivity(dashBoard)
        }
    }

    private fun findComponent() {
    cvDashboard = findViewById(R.id.cv_dashboard)
    cvPayment = findViewById(R.id.cv_payment)
    }
}