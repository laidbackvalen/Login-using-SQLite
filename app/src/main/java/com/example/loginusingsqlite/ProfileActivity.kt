package com.example.loginusingsqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        email = intent.getStringExtra("key_email").toString()
        getUserDetails()
    }

    private fun getUserDetails() {
        val dbHelper = DBHelper(this)
        val details: ArrayList<UserModel> = dbHelper.getLoggedinUserDetails(email)
        val userModel: UserModel = details.get(0)
        findViewById<TextView>(R.id.userNameTextProfile).text = userModel.name
        findViewById<TextView>(R.id.userEmailTextProfile).text = userModel.email
        findViewById<TextView>(R.id.userGenderTextProfile).text = userModel.gender
    }
}