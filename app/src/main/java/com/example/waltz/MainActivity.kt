package com.example.waltz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.waltz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val SPLASH_SCREEN = 5000

    private lateinit var topAnimation: Animation
    private lateinit var  bottomAnimation: Animation
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        binding.ivLogo.animation = topAnimation
        binding.tvLogo.animation = bottomAnimation

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, StartingActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN.toLong())

    }

}