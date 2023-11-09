package com.tanum.app.ui.main.lahan_saya.detail_lahan.aktivitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tanum.app.R
import com.tanum.app.data.model.LahanData
import com.tanum.app.databinding.ActivityDetailLahanBinding
import com.tanum.app.ui.main.MainActivity
import com.tanum.app.ui.main.lahan_saya.detail_lahan.form_aktivitas.FormAktivitasActivity
import com.tanum.app.ui.main.lahan_saya.form_lahan_saya.FormLahanActivity
import com.tanum.app.utils.AlertDialogHelper
import com.tanum.app.utils.DateFormatter
import com.tanum.app.utils.Result
import com.tanum.app.utils.calculateAge
import com.tanum.app.utils.getImage
import com.tanum.app.viewmodels.DetailLahanViewModel
import com.tanum.app.viewmodels.FormLahanViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class DetailLahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLahanBinding
    private lateinit var factory: ViewModelFactory
    private val detailLandViewModel: DetailLahanViewModel by viewModels { factory }
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this)
        binding = ActivityDetailLahanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val landObject = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_LAND, LahanData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_LAND)
        }

        if (landObject != null) {
            id = landObject.id
            setupView(landObject)
            setupAction(landObject)
        }
    }

    private fun setupView(landObject: LahanData) {
        val area = "${landObject.area}m²"
        val dateStart = DateFormatter.formatToFullDateFormat(landObject.dateStart)
        val age = calculateAge(landObject.dateStart)
        with(binding) {
            textViewNamaLahan.text = landObject.name
            textViewAlamatLahan.text = landObject.address
            textViewKepelimilikanValue.text = landObject.ownership
            textViewLuasValue.text = area
            textViewTanamanValue.text = landObject.plant
            textViewTanggalTanamValue.text = dateStart
            textViewUsiaTanamanValue.text = age
            textViewTotalBiayaValue.text = landObject.totalCost.toString()
            textViewKeuntunganValue.text = landObject.profit.toString()
            imageViewLahan.setImageResource(getImage(landObject.image))
        }
    }

    private fun setupAction(landObject: LahanData) {
        binding.buttonTambahkanAktivitas.setOnClickListener {
            val intent = Intent(this@DetailLahanActivity, FormAktivitasActivity::class.java)
            startActivity(intent)
        }
        binding.buttonEditDetailLahan.setOnClickListener {
            val intent = Intent(this@DetailLahanActivity, FormLahanActivity::class.java)
            intent.putExtra(FormLahanActivity.EXTRA_LAHAN, landObject)
            startActivity(intent)
        }
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.buttonDelete.setOnClickListener {
            AlertDialogHelper.showCompleteAlertDialog(this@DetailLahanActivity, "Hapus lahan", "Apakah anda yakin ingin menghapus lahan", "Yakin", "Tidak") {
                lifecycleScope.launch {
                    detailLandViewModel.token.collect { token ->
                        detailLandViewModel.deleteLand(token, id).observe(this@DetailLahanActivity) { result ->
                            if (result != null) {
                                when (result) {
                                    is Result.Loading -> {
                                        showLoading(true)
                                    }

                                    is Result.Error -> {
                                        showLoading(false)
                                        Toast.makeText(this@DetailLahanActivity,
                                            getString(R.string.failed_delete_land), Toast.LENGTH_SHORT).show()
                                    }

                                    is Result.Success -> {
                                        showLoading(false)
                                        Toast.makeText(this@DetailLahanActivity,
                                            getString(R.string.success_delete_land), Toast.LENGTH_SHORT).show()

                                        val intent = Intent(this@DetailLahanActivity, MainActivity::class.java)
                                        intent.putExtra(MainActivity.EXTRA_LAHAN_SAYA, true)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    companion object {
        const val EXTRA_LAND = "extra_land"
    }
}