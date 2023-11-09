package com.tanum.app.ui.main.lahan_saya

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanum.app.adapter.land.LandPagingAdapter
import com.tanum.app.data.model.LahanData
import com.tanum.app.databinding.FragmentLahanSayaBinding
import com.tanum.app.ui.main.lahan_saya.detail_lahan.aktivitas.DetailLahanActivity
import com.tanum.app.ui.main.lahan_saya.form_lahan_saya.FormLahanActivity
import com.tanum.app.utils.Result
import com.tanum.app.viewmodels.LahanSayaViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class LahanSayaFragment : Fragment() {

    private var _binding: FragmentLahanSayaBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val lahanSayaViewModel: LahanSayaViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLahanSayaBinding.inflate(inflater, container, false)
        factory = ViewModelFactory.getInstance(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            val adapter = LandPagingAdapter()
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.rvLands.layoutManager = layoutManager
            binding.rvLands.adapter = adapter
            lahanSayaViewModel.token.collect { token ->
                lahanSayaViewModel.getAllLand(token).observe(viewLifecycleOwner) { data ->
                    if (data != null) {
                        adapter.submitData(lifecycle, data)
                        binding.rvLands.post {
                            binding.rvLands.scrollToPosition(0)
                        }
                    }
                    if (adapter.itemCount != 0) {
                        binding.apply {
                            ivLahanSayaNull.visibility = View.GONE
                            tvLahanSayaNull.visibility = View.GONE
                            rvLands.visibility = View.VISIBLE
                        }
                    }
                    adapter.setOnItemClickCallback(object : LandPagingAdapter.OnItemClickCallback {
                        override fun onItemClicked(land: LahanData) {
                            val intent = Intent(requireActivity(), DetailLahanActivity::class.java)
                            intent.putExtra(DetailLahanActivity.EXTRA_LAND, land)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

    private fun setupAction() {
        binding.buttonTambahkanLahan.setOnClickListener{
            val intent = Intent(activity, FormLahanActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}