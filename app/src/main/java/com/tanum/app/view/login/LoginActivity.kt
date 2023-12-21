package com.tanum.app.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.tanum.app.R
import com.tanum.app.databinding.ActivityLoginBinding
import com.tanum.app.view.main.MainActivity
import com.tanum.app.view.register.RegisterActivity
import com.tanum.app.utils.AlertDialogHelper
import com.tanum.app.utils.Result
import com.tanum.app.viewmodels.LoginViewModel
import com.tanum.app.viewmodels.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupSpannableString()
        setupAction()
    }

    private fun setupAction() {

        binding.apply {
            buttonLogin.setOnClickListener {
                run {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken, 0)

                    val noHp = editTextNoHP.text.toString()
                    val password = editTextPassword.text.toString()

                    if (
                        noHp.isNotEmpty() and
                        password.isNotEmpty()
                    ) {
                        loginViewModel
                            .postLogin(noHp, password)
                            .observe(this@LoginActivity) { result ->
                                if (result != null) {
                                    when (result) {
                                        is Result.Loading -> {
                                            showLoading(true)
                                        }
                                        is Result.Success -> {
                                            showLoading(false)
                                            showSuccessDialog(
                                                getString(R.string.success),
                                                result.data,
                                                getString(R.string.next)
                                            )
                                        }
                                        is Result.Error -> {
                                            showLoading(false)
                                            val errorMessage = if (result.error != "") result.error else getString(R.string.register_failed_message)
                                            showFailedDialog(
                                                getString(R.string.failed),
                                                errorMessage,
                                                getString(R.string.next)
                                            )
                                        }
                                    }
                                }

                            }
                    } else {
                        showLoading(false)
                        showFailedDialog(
                            getString(R.string.failed),
                            getString(R.string.required_form),
                            getString(R.string.next)
                        )
                    }
                }
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

        spannableString.setSpan(clickableSpan, 18, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvRegister.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun showSuccessDialog(
        title: String,
        message: String,
        positiveButtonLabel: String
    ) {
        AlertDialogHelper.showAlertDialogPositive(
            this@LoginActivity,
            title,
            message,
            positiveButtonLabel
        ) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun showFailedDialog(
        title: String,
        message: String,
        positiveButtonLabel: String
    ) {
        AlertDialogHelper.showAlertDialogPositive(
            this@LoginActivity,
            title,
            message,
            positiveButtonLabel
        ) {}
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}