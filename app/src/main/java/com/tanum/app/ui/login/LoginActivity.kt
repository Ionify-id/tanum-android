package com.tanum.app.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import com.tanum.app.R
import com.tanum.app.databinding.ActivityLoginBinding
import com.tanum.app.ui.main.MainActivity
import com.tanum.app.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupSpannableString()
        setupAction()
    }

    private fun setupAction() {
        binding.buttonLogin.setOnClickListener{
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (email.isNotEmpty() and password.isNotEmpty()) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext,
                    getString(R.string.login_alert), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setupSpannableString() {
        val spannableString = SpannableString(getString(R.string.spannable_string_login))
        val clickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.apply {
                    isUnderlineText = false
                    color = applicationContext.getColor(R.color.green_tanum)
                }
            }
        }

        spannableString.setSpan(clickableSpan, 18, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvRegister.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}