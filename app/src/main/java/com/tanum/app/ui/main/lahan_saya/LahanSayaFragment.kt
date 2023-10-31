package com.tanum.app.ui.main.lahan_saya

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tanum.app.databinding.FragmentLahanSayaBinding
import com.tanum.app.ui.main.lahan_saya.form_lahan_saya.FormLahanActivity

class LahanSayaFragment : Fragment() {

    private var _binding: FragmentLahanSayaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLahanSayaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupAction()
        return root
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