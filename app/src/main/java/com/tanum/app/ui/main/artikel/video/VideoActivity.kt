package com.tanum.app.ui.main.artikel.video

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.adapter.VideoPagingAdapter
import com.tanum.app.data.model.VideoData
import com.tanum.app.databinding.ActivityVideoBinding
import com.tanum.app.ui.main.artikel.video.video_detail.VideoDetailActivity
import com.tanum.app.viewmodels.BeritaViewModel
import com.tanum.app.viewmodels.VideoViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private lateinit var factory: ViewModelFactory
    private val videoViewModel: VideoViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setupActionBar(binding.toolbar)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            val adapter = VideoPagingAdapter()
            binding.rvListVideo.layoutManager = LinearLayoutManager(this@VideoActivity)
            binding.rvListVideo.adapter = adapter
            videoViewModel.token.collect { token ->
                videoViewModel.getListVideo(token).observe(this@VideoActivity) { data ->
                    if (data != null) {
                        adapter.submitData(lifecycle, data)
                    }
                    adapter.setOnItemClickCallback(object : VideoPagingAdapter.OnItemClickCallback {
                        override fun onItemClicked(video: VideoData) {
                            val intentToDetail = Intent(this@VideoActivity, VideoDetailActivity::class.java)
                            intentToDetail.putExtra(VideoDetailActivity.EXTRA_URL, video.url)
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
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}