package com.tanum.app.ui.main.artikel.video

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.databinding.ActivityVideoBinding
import com.tanum.app.ui.main.artikel.berita.BeritaFragment

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setupActionBar(binding.toolbar)
        setupRecyclerViewFragment()
        setContentView(binding.root)
    }

    private fun setupRecyclerViewFragment() {
        val fragmentManager = supportFragmentManager
        val videoFragment = VideoFragment()
//        val fragment = fragmentManager.findFragmentByTag(BeritaFragment::class.java.simpleName)
        fragmentManager
            .beginTransaction()
            .add(com.tanum.app.R.id.fragmentContainerVideo, videoFragment, BeritaFragment::class.java.simpleName)
            .commit()
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