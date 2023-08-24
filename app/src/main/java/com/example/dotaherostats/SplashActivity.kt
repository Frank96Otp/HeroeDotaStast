package com.example.dotaherostats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dotaherostats.databinding.ActivitySplashBinding
import com.example.dotaherostats.onboarding.Onbording

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread = Thread {
            try {
                Thread.sleep(2000)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                val intent = Intent(this@SplashActivity, Onbording::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
        thread.start()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
