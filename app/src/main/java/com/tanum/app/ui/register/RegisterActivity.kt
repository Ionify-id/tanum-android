package com.tanum.app.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.tanum.app.R
import com.tanum.app.databinding.ActivityRegisterBinding
import com.tanum.app.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupDropdown()
        setupAction()
    }

    private fun setupAction() {
        binding.buttonRegister.setOnClickListener {
            val role = binding.autoCompleteTextViewRole.text.toString()
            val namaLengkap = binding.editTextNamaLengkap.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (role.isNotEmpty() and namaLengkap.isNotEmpty() and email.isNotEmpty() and password.isNotEmpty()) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            else {
                Toast.makeText(this, getString(R.string.register_alert), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDropdown() {
        val roles = resources.getStringArray(R.array.role)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, roles)
        binding.autoCompleteTextViewRole.setAdapter(arrayAdapter)
    }
}