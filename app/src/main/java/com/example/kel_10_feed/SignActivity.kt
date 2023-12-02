package com.example.kel_10_feed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kel_10_feed.databinding.ActivityLoginBinding
import com.example.kel_10_feed.databinding.ActivitySignBinding

class SignActivity : AppCompatActivity() {
    private val binding: ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.HaveBtn.setOnClickListener {
            val intent = Intent (this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

}