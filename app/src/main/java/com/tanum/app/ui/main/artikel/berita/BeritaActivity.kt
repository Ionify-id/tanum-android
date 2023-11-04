package com.tanum.app.ui.main.artikel.berita

import android.R.id.home
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.adapter.article.ArticlePagingAdapter
import com.tanum.app.data.remote.response.ArticleListItem
import com.tanum.app.databinding.ActivityBeritaBinding
import com.tanum.app.ui.main.artikel.berita.berita_detail.BeritaDetailActivity
import com.tanum.app.viewmodels.BeritaViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class BeritaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBeritaBinding
    private lateinit var factory: ViewModelFactory
    private val beritaViewModel: BeritaViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this)
        binding = ActivityBeritaBinding.inflate(layoutInflater)
        setupActionBar(binding.toolbar)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            val adapter = ArticlePagingAdapter()
            binding.rvListBerita.layoutManager = LinearLayoutManager(this@BeritaActivity)
            binding.rvListBerita.adapter = adapter
            beritaViewModel.token.collect { token ->
                beritaViewModel.getListArticle(token).observe(this@BeritaActivity) { data ->
                    if (data != null) {
                        adapter.submitData(lifecycle, data)
                    }
                    adapter.setOnItemClickCallback(object : ArticlePagingAdapter.OnItemClickCallback {
                        override fun onItemClicked(article: ArticleListItem) {
                            val intentToDetail = Intent(this@BeritaActivity, BeritaDetailActivity::class.java)
                            intentToDetail.putExtra(BeritaDetailActivity.EXTRA_ID, article.id)
                            startActivity(intentToDetail)
                        }
                    })
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
            home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}