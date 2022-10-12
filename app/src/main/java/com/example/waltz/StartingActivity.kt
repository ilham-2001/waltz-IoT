package com.example.waltz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.waltz.databinding.ActivityStartingBinding

class StartingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityStartingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_get_started -> startActivity(Intent(this, MainMenuActivity::class.java))
            R.id.btn_login -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}