package com.tanum.app.view.main.lahan_saya.form_lahan_saya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.R
import com.tanum.app.model.data.body.LahanBody
import com.tanum.app.databinding.ActivityFormLahanBinding
import com.tanum.app.view.main.MainActivity
import com.tanum.app.view.main.lahan_saya.detail_lahan.aktivitas.DetailLahanActivity
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
    private val landFormViewModel: FormLahanViewModel by viewModels { factory }
    private var image = ""
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this@FormLahanActivity)
        binding = ActivityFormLahanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar(binding.toolbar)
        id = intent.getIntExtra(EXTRA_LAND_ID, 0)
        if (id != 0) {
            setupEditView()
            setupEditAction()
        } else {
            setupCreateAction()
        }
    }

    private fun setupEditView() {
        showLoading(false)
        lifecycleScope.launch {
            landFormViewModel.token.collect { token ->
                landFormViewModel.getDetailLand(id, token).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            val land = result.data
                            image = land.image
                            binding.apply {
                                textInputEditTextLahanSaya.setText(land.name)
                                textInputEditTextAlamat.setText(land.address)
                                autoCompleteTextViewKepemilikan.setText(land.ownership, false)
                                textInputEditTextLuas.setText(land.area.toString())
                                textInputEditTextTanaman.setText(land.plant)
                                textInputEditTextVarietas.setText(land.varietas)
                                textInputEditTanggal.setText(DateFormatter.formatToFullDateFormat(land.dateStart))
                            }
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this@FormLahanActivity,
                                getString(R.string.failed_to_load_land_detail), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupEditAction() {
        setupDropdown()
        var dateStart: String
        binding.textInputEditTanggal.setOnClickListener {
            DatePickerHelper.showDatePickerDialog(this, null) { selectedDate ->
                binding.textInputEditTanggal.setText(selectedDate)
            }
        }
        showLoading(false)
        lifecycleScope.launch {
            landFormViewModel.token.collect { token ->
                var land: LahanBody
                var landName: String
                var address: String
                var ownership: String
                var area: Float
                var plant: String
                var varietas: String
                binding.buttonSimpan.setOnClickListener {
                    binding.apply {
                        landName = textInputEditTextLahanSaya.text.toString()
                        address = textInputEditTextAlamat.text.toString()
                        ownership = autoCompleteTextViewKepemilikan.text.toString()
                        val textArea = textInputEditTextLuas.text.toString()
                        area = if (textArea.isEmpty()) {
                            0.0f
                        } else {
                            textArea.toFloatOrNull() ?: 0.0f
                        }
                        plant = textInputEditTextTanaman.text.toString()
                        varietas = textInputEditTextVarietas.text.toString()
                        dateStart = DateFormatter.formatToZFormat(textInputEditTanggal.text.toString())
                        land = LahanBody( landName, address, ownership, area, plant, varietas, dateStart, image)
                    }
                    if ( landName.isEmpty() || address.isEmpty() || ownership.isEmpty() || area == 0.0f || plant.isEmpty() || varietas.isEmpty() || dateStart.isEmpty() ) {
                        Toast.makeText(this@FormLahanActivity,
                            getString(R.string.form_need_to_fill), Toast.LENGTH_SHORT).show()
                    } else {
                        landFormViewModel.editLand(id, token, land).observe(this@FormLahanActivity) { result ->
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
                                            getString(R.string.next),
                                            "edit"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupCreateAction() {
        setupDropdown()
        var dateStart = ""
        binding.textInputEditTanggal.setOnClickListener {
            DatePickerHelper.showDatePickerDialog(this, null) { selectedDate ->
                binding.textInputEditTanggal.setText(selectedDate)
                dateStart = DateFormatter.formatToZFormat(selectedDate)
            }
        }
        showLoading(false)
        lifecycleScope.launch {
            landFormViewModel.token.collect { token ->
                var land: LahanBody
                var landName: String
                var address: String
                var ownership: String
                var area: Float
                var plant: String
                var varietas: String
                binding.buttonSimpan.setOnClickListener {
                    binding.apply {
                        landName = textInputEditTextLahanSaya.text.toString()
                        address = textInputEditTextAlamat.text.toString()
                        ownership = autoCompleteTextViewKepemilikan.text.toString()
                        val textArea = textInputEditTextLuas.text.toString()
                        area = if (textArea.isEmpty()) {
                            0.0f
                        } else {
                            textArea.toFloatOrNull() ?: 0.0f
                        }
                        plant = textInputEditTextTanaman.text.toString()
                        varietas = textInputEditTextVarietas.text.toString()
                        land = LahanBody( landName, address, ownership, area, plant, varietas, dateStart, getRandomNumber())
                    }
                    if ( landName.isEmpty() || address.isEmpty() || ownership.isEmpty() || area == 0.0f || plant.isEmpty() || varietas.isEmpty() || dateStart.isEmpty() ) {
                        Toast.makeText(this@FormLahanActivity,
                            getString(R.string.form_need_to_fill), Toast.LENGTH_SHORT).show()
                    } else {
                        landFormViewModel.createLand(token, land).observe(this@FormLahanActivity) { result ->
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
                                        Log.d("result success", "success")
                                        showLoading(false)
                                        showSuccessDialog(
                                            getString(R.string.success),
                                            result.data,
                                            getString(R.string.next),
                                            "create"
                                        )
                                    }
                                }
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
        positiveButtonLabel: String,
        action: String
    ) {
        AlertDialogHelper.showAlertDialogPositive(
            this@FormLahanActivity,
            title,
            message,
            positiveButtonLabel
        ) {
            formAction(action)
        }
    }

    private fun formAction(action: String) {
        when (action) {
            "create" -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_LAHAN_SAYA, true)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
            "edit" -> {
                val intent = Intent(this@FormLahanActivity, DetailLahanActivity::class.java)
                intent.putExtra(DetailLahanActivity.EXTRA_LAND_ID, id)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }

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
        const val EXTRA_LAND_ID = "extra_land_id"
    }
}