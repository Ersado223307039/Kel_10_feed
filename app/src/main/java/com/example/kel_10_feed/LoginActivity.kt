package com.example.kel_10_feed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kel_10_feed.databinding.ActivityLoginBinding
import com.example.kel_10_feed.databinding.ActivitySignBinding

class LoginActivity : AppCompatActivity() {
    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.LoginBtn.setOnClickListener {
            val intent = Intent (this,SignActivity::class.java)
            startActivity(intent)
        }
        binding.dontHaveBtn.setOnClickListener {
            val intent = Intent (this,SignActivity::class.java)
            startActivity(intent)
        }
    }
}