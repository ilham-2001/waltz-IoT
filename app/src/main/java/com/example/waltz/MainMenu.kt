package com.example.waltz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainMenu : AppCompatActivity() {

    private lateinit var cvDashboard: CardView
    private lateinit var cvPayment: CardView
    private lateinit var bsFrame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar!!.hide()

        setContentView(R.layout.activity_main_menu)

        findComponent()

        BottomSheetBehavior.from(bsFrame).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        cvDashboard.setOnClickListener {
            val dashBoard = Intent(this, DashBoard::class.java)
            startActivity(dashBoard)
        }
    }

    private fun findComponent() {
    cvDashboard = findViewById(R.id.cv_dashboard)
    cvPayment = findViewById(R.id.cv_payment)
    bsFrame = findViewById(R.id.fr_mainmenu)
    }
}