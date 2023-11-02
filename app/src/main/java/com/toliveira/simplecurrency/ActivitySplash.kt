package com.toliveira.simplecurrency

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.toliveira.simplecurrency.databinding.ActivitySplashBinding


private lateinit var binding: ActivitySplashBinding

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //FullScreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //Timer
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@ActivitySplash, MainActivity::class.java))
        }, 1500)


    }


}