package com.tanum.app.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tanum.app.R
import com.tanum.app.databinding.ActivityRegisterBinding
import com.tanum.app.ui.login.LoginActivity
import com.tanum.app.utils.AlertDialogHelper
import com.tanum.app.utils.Result
import com.tanum.app.viewmodels.RegisterViewModel
import com.tanum.app.viewmodels.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupDropdown()
        setupAction()
    }

    private fun setupAction() {

        binding.apply {
            buttonRegister.setOnClickListener {
                run {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken, 0)

                    val job = autoCompleteTextViewRole.text.toString()
                    val email = editTextEmail.text.toString()
                    val password = editTextPassword.text.toString()
                    val fullName = editTextNamaLengkap.text.toString()

                    if (
                        job.isNotEmpty() and
                        email.isNotEmpty() and
                        password.isNotEmpty() and
                        fullName.isNotEmpty()
                    ) {
                        registerViewModel
                            .postRegister(job, email, password, fullName)
                            .observe(this@RegisterActivity) { result ->
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

    private fun setupDropdown() {
        val roles = resources.getStringArray(R.array.role)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, roles)
        binding.autoCompleteTextViewRole.setAdapter(arrayAdapter)
    }

    private fun showSuccessDialog(
        title: String,
        message: String,
        positiveButtonLabel: String
    ) {
        AlertDialogHelper.showAlertDialogPositive(
            this@RegisterActivity,
            title,
            message,
            positiveButtonLabel
        ) {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
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
            this@RegisterActivity,
            title,
            message,
            positiveButtonLabel
        ) {}
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}