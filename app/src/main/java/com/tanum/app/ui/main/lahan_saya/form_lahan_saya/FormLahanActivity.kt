package com.tanum.app.ui.main.lahan_saya.form_lahan_saya

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.R
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.model.body.LahanBody
import com.tanum.app.databinding.ActivityFormLahanBinding
import com.tanum.app.ui.main.MainActivity
import com.tanum.app.utils.AlertDialogHelper
import com.tanum.app.utils.DateFormatter
import com.tanum.app.utils.DatePickerHelper
import com.tanum.app.utils.Result
import com.tanum.app.utils.getRandomNumber
import com.tanum.app.viewmodels.FormLahanViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class FormLahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormLahanBinding
    private lateinit var factory: ViewModelFactory
    private val landViewModel: FormLahanViewModel by viewModels { factory }
    private var dateStart: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this)
        binding = ActivityFormLahanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar(binding.toolbar)
        val landObject = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_LAHAN, LahanData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_LAHAN)
        }

        if (landObject != null) {
            val id = landObject.id
            val image = landObject.image
            setupEditView(landObject)
            setupEditAction(id, image)
        } else {
            setupCreateAction()
        }
    }

    private fun setupEditView(landObject: LahanData) {
        binding.apply {
            textInputEditTextLahanSaya.setText(landObject.name)
            textInputEditTextAlamat.setText(landObject.address)
            autoCompleteTextViewKepemilikan.setText(landObject.ownership)
            textInputEditTextLuas.setText(landObject.area.toString())
            textInputEditTextTanaman.setText(landObject.plant)
            textInputEditVarietas.setText(landObject.varietas)
            textInputEditTanggal.setText(DateFormatter.formatToFullDateFormat(landObject.dateStart))
        }
        dateStart = landObject.dateStart
    }

    private fun setupEditAction(id: Int, image: String) {
        setupDropdown()
        binding.textInputEditTanggal.setOnClickListener {
            DatePickerHelper.showDatePickerDialog(this, null) { selectedDate ->
                binding.textInputEditTanggal.setText(selectedDate)
                dateStart = DateFormatter.formatToZFormat(selectedDate)
            }
        }
        showLoading(false)
        lifecycleScope.launch {
            landViewModel.token.collect { token ->
                observeEditLahan(token, id, image)
            }
        }
    }

    private fun setupCreateAction() {
        setupDropdown()
        binding.textInputEditTanggal.setOnClickListener {
            DatePickerHelper.showDatePickerDialog(this, null) { selectedDate ->
                binding.textInputEditTanggal.setText(selectedDate)
                dateStart = DateFormatter.formatToZFormat(selectedDate)
            }
        }
        showLoading(false)
        lifecycleScope.launch {
            landViewModel.token.collect { token ->
                observeCreateLahan(token)
            }
        }
    }

    private fun observeEditLahan(token: String, id: Int, image: String) {
        var land: LahanBody
        var landName: String
        var address: String
        var ownership: String
        var area: Int
        var plant: String
        var varietas: String
        binding.buttonSimpan.setOnClickListener {
            binding.apply {
                landName = textInputEditTextLahanSaya.text.toString()
                address = textInputEditTextAlamat.text.toString()
                ownership = autoCompleteTextViewKepemilikan.text.toString()
                val textArea = textInputEditTextLuas.text.toString()
                area = if (textArea.isEmpty()) 0 else textArea.toIntOrNull() ?: 0
                plant = textInputEditTextTanaman.text.toString()
                varietas = textInputEditVarietas.text.toString()
                land = LahanBody( landName, address, ownership, area, plant, varietas, dateStart, image)
            }
            if ( landName.isEmpty() || address.isEmpty() || ownership.isEmpty() || area == 0 || plant.isEmpty() || varietas.isEmpty() || dateStart.isEmpty() ) {
                Toast.makeText(this@FormLahanActivity,
                    getString(R.string.form_need_to_fill), Toast.LENGTH_SHORT).show()
            } else {
                landViewModel.editLand(id, token, land).observe(this@FormLahanActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Error -> {
                                showLoading(false)
                                showFailedDialog(
                                    getString(R.string.failed),
                                    result.error,
                                    getString(R.string.next)
                                )
                            }
                            is Result.Success -> {
                                showLoading(false)
                                showSuccessDialog(
                                    getString(R.string.success),
                                    getString(R.string.land_edit_success),
                                    getString(R.string.next)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeCreateLahan(token: String) {
        var land: LahanBody
        var landName: String
        var address: String
        var ownership: String
        var area: Int
        var plant: String
        var varietas: String
        binding.buttonSimpan.setOnClickListener {
            binding.apply {
                landName = textInputEditTextLahanSaya.text.toString()
                address = textInputEditTextAlamat.text.toString()
                ownership = autoCompleteTextViewKepemilikan.text.toString()
                val textArea = textInputEditTextLuas.text.toString()
                area = if (textArea.isEmpty()) 0 else textArea.toIntOrNull() ?: 0
                plant = textInputEditTextTanaman.text.toString()
                varietas = textInputEditVarietas.text.toString()
                land = LahanBody( landName, address, ownership, area, plant, varietas, dateStart, getRandomNumber())
            }
            if ( landName.isEmpty() || address.isEmpty() || ownership.isEmpty() || area == 0 || plant.isEmpty() || varietas.isEmpty() || dateStart.isEmpty() ) {
                Toast.makeText(this@FormLahanActivity,
                    getString(R.string.form_need_to_fill), Toast.LENGTH_SHORT).show()
            } else {
                landViewModel.createLand(token, land).observe(this@FormLahanActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Error -> {
                                showLoading(false)
                                showFailedDialog(
                                    getString(R.string.failed),
                                    result.error,
                                    getString(R.string.next)
                                )
                            }
                            is Result.Success -> {
                                showLoading(false)
                                showSuccessDialog(
                                    getString(R.string.success),
                                    result.data,
                                    getString(R.string.next)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupDropdown() {
        val roles = resources.getStringArray(R.array.kepemilikan)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, roles)
        binding.autoCompleteTextViewKepemilikan.setAdapter(arrayAdapter)
    }

    private fun setupActionBar(toolbar: MaterialToolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSuccessDialog(
        title: String,
        message: String,
        positiveButtonLabel: String
    ) {
        AlertDialogHelper.showAlertDialogPositive(
            this@FormLahanActivity,
            title,
            message,
            positiveButtonLabel
        ) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_LAHAN_SAYA, true)
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
            this@FormLahanActivity,
            title,
            message,
            positiveButtonLabel
        ) {}
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_LAHAN = "extra_lahan"
    }
}