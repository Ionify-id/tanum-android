package com.tanum.app.ui.main.artikel.berita.berita_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tanum.app.R
import com.tanum.app.databinding.FragmentBeritaBinding
import com.tanum.app.databinding.FragmentBeritaDetailBinding

class BeritaDetailFragment : Fragment() {

    private var _binding: FragmentBeritaDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeritaDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

}