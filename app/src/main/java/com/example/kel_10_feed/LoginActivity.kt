package com.example.kel_10_feed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kel_10_feed.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=Firebase.auth
        database=FirebaseDatabase.getInstance()


        binding.loginButton.setOnClickListener {
            email=binding.emailAddress.text.toString().trim()
            password=binding.password.text.toString().trim()

            if(email.isBlank()||password.isBlank()){
                Toast.makeText(this,"please fill all the detail", Toast.LENGTH_SHORT).show()
            }else{
                createUser()
                Toast.makeText(this,"Login Succes", Toast.LENGTH_SHORT).show()

            }


        }
        binding.dontHaveBtn.setOnClickListener {
            val intent = Intent (this,SignActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful){
                val user=auth.currentUser
                updateUi(user)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        updateUi(user)
                    }else{
                        Toast.makeText(this,"Sign in Failed", Toast.LENGTH_SHORT).show()

                    }

                }

            }
        }

    }

    private fun updateUi(user: FirebaseUser?) {
        val intent = Intent (this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}