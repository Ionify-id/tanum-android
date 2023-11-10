package com.tanum.app.ui.main.lahan_saya.detail_lahan.form_aktivitas

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
import com.tanum.app.data.model.body.AktivitasBody
import com.tanum.app.databinding.ActivityFormAktivitasBinding
import com.tanum.app.ui.main.MainActivity
import com.tanum.app.ui.main.lahan_saya.detail_lahan.aktivitas.DetailLahanActivity
import com.tanum.app.utils.AlertDialogHelper
import com.tanum.app.utils.DateFormatter
import com.tanum.app.utils.DatePickerHelper
import com.tanum.app.utils.Result
import com.tanum.app.viewmodels.FormAktivitasViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class FormAktivitasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormAktivitasBinding
    private lateinit var factory: ViewModelFactory
    private val activityFormViewModel: FormAktivitasViewModel by viewModels { factory }
    private var landId = -1
    private var dateAction: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAktivitasBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this@FormAktivitasActivity)
        landId = intent.getIntExtra(EXTRA_LAND_ID, -1)
        setContentView(binding.root)
        setupView()
        setupCreateAction(landId)
    }

    private fun setupCreateAction(landId: Int) {
        setupDropdown()
        binding.textInputEditWaktuKegiatan.setOnClickListener {
            DatePickerHelper.showDatePickerDialog(this, null) { selectedDate ->
                binding.textInputEditWaktuKegiatan.setText(selectedDate)
                dateAction = DateFormatter.formatToZFormat(selectedDate)
            }
        }
        showLoading(false)
        lifecycleScope.launch {
            activityFormViewModel.token.collect { token ->
                var category: String
                var action: String
                var cost: Int
                var activityBody: AktivitasBody
                binding.buttonSimpan.setOnClickListener {
                    binding.apply {
                        category = autoCompleteTextViewKategori.text.toString()
                        action = autoCompleteTextViewKegiatan.text.toString()
                        val textCost = textInputEditTextBiaya.text.toString()
                        cost = if (textCost.isEmpty()) 0 else textCost.toIntOrNull() ?: 0
                        activityBody = AktivitasBody(category, action, cost, dateAction)
                    }
                    if (category.isEmpty() || action.isEmpty() || cost == 0 || dateAction.isEmpty()) {
                        Toast.makeText(this@FormAktivitasActivity,
                            getString(R.string.form_need_to_fill), Toast.LENGTH_SHORT).show()
                    } else {
                        activityFormViewModel.createActivity(landId, token, activityBody).observe(this@FormAktivitasActivity) { result ->
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
        }
    }

    private fun setupView() {
        setupActionBar(binding.toolbar)
    }

    private fun setupActionBar(toolbar: MaterialToolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupDropdown() {
        val kategori = resources.getStringArray(R.array.kategori)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, kategori)
        binding.autoCompleteTextViewKategori.setAdapter(arrayAdapter)

        binding.autoCompleteTextViewKategori.setOnItemClickListener { _, _, position, _ ->
            val kategoriValue = kategori[position]
            val kegiatan = when (kategoriValue) {
                "Pra-tanam" -> resources.getStringArray(R.array.pra_tanam)
                "Tanam" -> resources.getStringArray(R.array.tanam)
                "Pemeliharaan" -> resources.getStringArray(R.array.pemeliharaan)
                "Panen" -> resources.getStringArray(R.array.panen)
                "Penjualan" -> resources.getStringArray(R.array.penjualan)
                else -> null
            }
            val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_item, kegiatan ?: arrayOf())
            binding.autoCompleteTextViewKegiatan.setAdapter(arrayAdapter2)
            binding.autoCompleteTextViewKegiatan.setText("", false)
        }
    }

    private fun showSuccessDialog(
        title: String,
        message: String,
        positiveButtonLabel: String
    ) {
        AlertDialogHelper.showAlertDialogPositive(
            this@FormAktivitasActivity,
            title,
            message,
            positiveButtonLabel
        ) {
            val intent = Intent(this, DetailLahanActivity::class.java)
            intent.putExtra(DetailLahanActivity.EXTRA_LAND_ID, landId)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
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
            this@FormAktivitasActivity,
            title,
            message,
            positiveButtonLabel
        ) {}
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_LAND_ID = "extra_land_id"
    }
}