package com.example.waltz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val SPLASH_SCREEN = 5000

    private lateinit var topAnimation: Animation
    private lateinit var  bottomAnimation: Animation

    private lateinit var ivLogo: ImageView
    private lateinit var tvLogo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.hide()

        findComponent()

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        ivLogo.animation = topAnimation
        tvLogo.animation = bottomAnimation

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN.toLong())

    }

    private fun findComponent() {
        ivLogo = findViewById(R.id.ivLogo)
        tvLogo = findViewById(R.id.tvLogo)
    }
}