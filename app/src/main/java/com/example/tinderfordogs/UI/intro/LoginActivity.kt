package com.example.tinderfordogs.UI.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.tinderfordogs.MainActivity
import com.example.tinderfordogs.R
import com.example.tinderfordogs.databinding.ActivityLoginBinding
import com.example.tinderfordogs.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.llCreateAccount.setOnClickListener {
            startActivity(Intent(this, CreateUserActivity::class.java))
            finish()
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnLogin.setOnClickListener {
            binding.progressBar.isVisible=true
            val email = binding.etEmailLogin.text.toString()
            val pass = binding.etPasswordLogin.text.toString()
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmailLogin.requestFocus()
                binding.etEmailLogin.error = "Please Enter Valid Email"
            } else if (pass.isNullOrEmpty() || pass.length <= 6) {
                binding.etPasswordLogin.requestFocus()
                binding.etPasswordLogin.error = "Please enter more than 6 characters"
            } else {
                loginWithFirebase(email, pass)
            }
        }


    }

    private fun loginWithFirebase(email: String, pass: String) {
        viewModel.loginUser(email, pass)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { result ->
            result.onSuccess {
                binding.progressBar.isVisible=false
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            }.onFailure {
                binding.progressBar.isVisible=false
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginOptionsActivity::class.java))
        finish()
    }


}

