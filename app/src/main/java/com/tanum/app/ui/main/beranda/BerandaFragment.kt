package com.tanum.app.ui.main.beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tanum.app.R
import com.tanum.app.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(BerandaViewModel::class.java)

        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupView()
        setupAction()
        return root
    }

    private fun setupAction() {
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)

        binding.tvLihatLahanLainnya.setOnClickListener {
            bottomNav?.selectedItemId = R.id.navigation_lahan_saya
        }
        binding.tvArtikelLainnya.setOnClickListener {
            bottomNav?.selectedItemId = R.id.navigation_artikel
        }
    }

    private fun setupView() {
        val welcomeTextHtml = resources.getText(R.string.welcome_message_html).toString()
        val styledText = HtmlCompat.fromHtml(welcomeTextHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvWelcomeBeranda.text = styledText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}