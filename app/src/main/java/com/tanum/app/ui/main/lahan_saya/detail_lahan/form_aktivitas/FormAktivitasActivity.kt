package com.tanum.app.ui.main.lahan_saya.detail_lahan.form_aktivitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.R
import com.tanum.app.databinding.ActivityFormAktivitasBinding
import com.tanum.app.utils.DatePickerHelper

class FormAktivitasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormAktivitasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAktivitasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.textInputEditWaktuKegiatan.setOnClickListener {
            DatePickerHelper.showDatePickerDialog(this, null) { selectedDate ->
                binding.textInputEditWaktuKegiatan.setText(selectedDate)
            }
        }
    }

    private fun setupView() {
        setupActionBar(binding.toolbar)
        setupDropdown()
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