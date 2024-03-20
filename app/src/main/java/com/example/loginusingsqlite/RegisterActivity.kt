package com.example.loginusingsqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    lateinit var gender: String //global var
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nameEditText = findViewById<TextInputEditText>(R.id.userNameRegistrationActivity)
        val emailEditText = findViewById<TextInputEditText>(R.id.userEmailRegistrationActivity)
        val passwordEditText = findViewById<TextInputEditText>(R.id.userPassRegistrationActivity)

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            var name = nameEditText.text.toString()
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()
            //Gender
            val radioGroupGender = findViewById<RadioGroup>(R.id.registrationGenderRadioGroup)
            val selectedRadioButton = findViewById<RadioButton>(radioGroupGender.checkedRadioButtonId)
            if (selectedRadioButton != null) {
                gender = selectedRadioButton.text.toString()
            } else {
                Toast.makeText(this, "Select your Gender", Toast.LENGTH_SHORT).show()
            }
            //Registeration function call
            val dbHelper = DBHelper(this)
            dbHelper.registerUser(name, email, password, gender)
            //Setting vTextInputEditText Values to null after clicking on register button
            nameEditText.setText("")
            emailEditText.setText("")
            passwordEditText.setText("")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}