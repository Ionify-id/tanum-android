package com.tanum.app.ui.main.artikel.berita

import android.R.id.home
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.appbar.MaterialToolbar
import com.tanum.app.R
import com.tanum.app.databinding.ActivityBeritaBinding

class BeritaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBeritaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeritaBinding.inflate(layoutInflater)
        setupActionBar(binding.toolbar)
        setupRecyclerViewFragment()
        setContentView(binding.root)
    }

    private fun setupRecyclerViewFragment() {
        val fragmentManager = supportFragmentManager
        val beritaFragment = BeritaFragment()
//        val fragment = fragmentManager.findFragmentByTag(BeritaFragment::class.java.simpleName)
        fragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerBerita, beritaFragment, BeritaFragment::class.java.simpleName)
            .commit()
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