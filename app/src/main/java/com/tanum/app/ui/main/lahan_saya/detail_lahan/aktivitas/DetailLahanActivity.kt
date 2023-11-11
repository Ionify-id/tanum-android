package com.tanum.app.ui.main.lahan_saya.detail_lahan.aktivitas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanum.app.R
import com.tanum.app.adapter.activity.ActivityPagingAdapter
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
        id = intent.getIntExtra(EXTRA_LAND_ID, 0)
        setupView()
        setupAction()
    }

    private fun setupView() {
        setupDetailLahanView()
        setupActivityView()
    }

    private fun setupDetailLahanView() {
        showLoading(false)
        lifecycleScope.launch {
            detailLandViewModel.token.collect { token ->
                detailLandViewModel.getDetailLand(id, token).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            val land = result.data
                            val area = "${land.area}m²"
                            val dateStart = DateFormatter.formatToFullDateFormat(land.dateStart)
                            val age = calculateAge(land.dateStart)
                            var profit = 0
                            binding.apply {
                                textViewNamaLahan.text = land.name
                                textViewAlamatLahan.text = land.address
                                textViewKepelimilikanValue.text = land.ownership
                                textViewLuasValue.text = area
                                textViewTanamanValue.text = land.plant
                                textViewTanggalTanamValue.text = dateStart
                                textViewVarietasValue.text = land.varietas
                                textViewUsiaTanamanValue.text = age
                                textViewTotalBiayaValue.text = land.totalCost.toString()
                                if (land.profit > 0) profit = land.profit
                                textViewKeuntunganValue.text = profit.toString()
                                imageViewLahan.setImageResource(getImage(land.image))
                            }
                        }
                        is Result.Error -> {
                            Log.d("result", "result loading")
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun setupActivityView() {
        showLoading(false)
        lifecycleScope.launch {
            val adapter = ActivityPagingAdapter()
            val layoutManager = LinearLayoutManager(this@DetailLahanActivity)
            binding.recyclerViewAktivitas.layoutManager = layoutManager
            binding.recyclerViewAktivitas.adapter = adapter
            detailLandViewModel.token.collect { token ->
                detailLandViewModel.getAllActivities(id, token).observe(this@DetailLahanActivity) { data ->
                    if (data != null) {
                        adapter.submitData(lifecycle, data)
                        binding.recyclerViewAktivitas.post {
                            binding.recyclerViewAktivitas.scrollToPosition(0)
                        }
                        adapter.setOnItemClickCallback(object : ActivityPagingAdapter.OnItemClickCallback{
                            override fun onItemEditClick(activityId: Int) {
                                val intent = Intent(this@DetailLahanActivity, FormAktivitasActivity::class.java)
                                intent.putExtra(FormAktivitasActivity.EXTRA_ACTIVITY_ID, activityId)
                                startActivity(intent)
                            }

                            override fun onItemDeleteClick(activityId: Int) {
                                AlertDialogHelper.showCompleteAlertDialog(
                                    this@DetailLahanActivity,
                                    "Hapus aktivitas",
                                    "Apakah anda yakin ingin menghapus aktivitas?",
                                    "Yakin",
                                    "Tidak"
                                ) {
                                    showLoading(false)
                                    lifecycleScope.launch {
                                        detailLandViewModel.token.collect { token ->
                                            detailLandViewModel.deleteActivity(activityId, token).observe(this@DetailLahanActivity) { result ->
                                                if (result != null) {
                                                    when (result) {
                                                        is Result.Loading -> {
                                                            showLoading(true)
                                                        }
                                                        is Result.Success -> {
                                                            showLoading(false)
                                                            Toast.makeText(this@DetailLahanActivity,
                                                                getString(R.string.success_delete_activity), Toast.LENGTH_SHORT).show()
                                                            onResume()
                                                        }
                                                        is Result.Error -> {
                                                            showLoading(false)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        })
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.buttonTambahkanAktivitas.setOnClickListener {
            val intent = Intent(this@DetailLahanActivity, FormAktivitasActivity::class.java)
            intent.putExtra(FormAktivitasActivity.EXTRA_LAND_ID, id)
            startActivity(intent)
        }
        binding.buttonEditDetailLahan.setOnClickListener {
            val intent = Intent(this@DetailLahanActivity, FormLahanActivity::class.java)
            intent.putExtra(FormLahanActivity.EXTRA_LAND_ID, id)
            startActivity(intent)
        }
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.buttonDelete.setOnClickListener {
            AlertDialogHelper.showCompleteAlertDialog(this@DetailLahanActivity, "Hapus lahan", "Apakah anda yakin ingin menghapus lahan?", "Yakin", "Tidak") {
                lifecycleScope.launch {
                    detailLandViewModel.token.collect { token ->
                        detailLandViewModel.deleteLand(id, token).observe(this@DetailLahanActivity) { result ->
                            if (result != null) {
                                when (result) {
                                    is Result.Loading -> {
                                        showLoading(true)
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
                                    is Result.Error -> {
                                        showLoading(false)
                                        Toast.makeText(this@DetailLahanActivity,
                                            getString(R.string.failed_delete_land), Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()
        setupDetailLahanView()
        setupActivityView()
    }

    companion object {
        const val EXTRA_LAND_ID = "extra_land_id"
    }
}