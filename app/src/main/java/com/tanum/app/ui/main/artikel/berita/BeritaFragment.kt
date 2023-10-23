package com.tanum.app.ui.main.artikel.berita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tanum.app.R
import com.tanum.app.databinding.FragmentArtikelBinding
import com.tanum.app.databinding.FragmentBeritaBinding
import com.tanum.app.ui.main.artikel.berita.berita_detail.BeritaDetailFragment

class BeritaFragment : Fragment() {

    private var _binding: FragmentBeritaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeritaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonDetail.setOnClickListener {
            val beritaDetailFragment = BeritaDetailFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerBerita, beritaDetailFragment, BeritaDetailFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        return root
    }

}