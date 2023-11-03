package com.tanum.app.ui.main.artikel.berita.berita_detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.R
import com.tanum.app.databinding.ActivityBeritaDetailBinding
import com.tanum.app.utils.AlertDialogHelper
import com.tanum.app.utils.Result
import com.tanum.app.viewmodels.BeritaDetailViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class BeritaDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBeritaDetailBinding
    private lateinit var factory: ViewModelFactory
    private val beritaDetailViewModel: BeritaDetailViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this)
        binding = ActivityBeritaDetailBinding.inflate(layoutInflater)
        setupActionBar(binding.toolbar)
        setupView()
        setContentView(binding.root)
    }

    private fun setupView() {
        lifecycleScope.launch {
            val id = intent.getIntExtra(EXTRA_ID, 0)
            beritaDetailViewModel.token.collect { token ->
                beritaDetailViewModel.getArticleDetail(token, id).collect { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                val news = result.data
                                with(binding) {
                                    tvBeritaTitle.text = news.title
                                    tvBeritaIsi.text = news.description
                                    Glide.with(this@BeritaDetailActivity)
                                        .load(news.thumbnail)
                                        .into(ivBerita)
                                }
                            }
                            is Result.Error -> {
                                showLoading(false)
                                AlertDialogHelper.showAlertDialogPositive(
                                    this@BeritaDetailActivity,
                                    getString(R.string.failed),
                                    getString(R.string.failed_news),
                                    getString(R.string.next)
                                ) {}
                            }
                        }
                    }
                }
            }
        }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}