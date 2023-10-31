package com.tanum.app.ui.main.lahan_saya.form_lahan_saya

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.R
import com.tanum.app.databinding.ActivityFormLahanBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FormLahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormLahanBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar(binding.toolbar)
        setupAction()
    }

    private fun setupAction() {
        setupDropdown()
        binding.textInputEditTanggal.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { DatePicker, year: Int, month: Int, day: Int ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.textInputEditTanggal.setText(formattedDate)
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
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
}