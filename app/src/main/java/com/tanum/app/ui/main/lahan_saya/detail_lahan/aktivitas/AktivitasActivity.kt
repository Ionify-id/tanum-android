package com.tanum.app.ui.main.lahan_saya.detail_lahan.aktivitas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tanum.app.databinding.ActivityAktivitasBinding
import com.tanum.app.ui.main.lahan_saya.detail_lahan.form_aktivitas.FormAktivitasActivity

class AktivitasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAktivitasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAktivitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.buttonTambahkanAktivitas.setOnClickListener {
            val intent = Intent(this@AktivitasActivity, FormAktivitasActivity::class.java)
            startActivity(intent)
        }
    }
}