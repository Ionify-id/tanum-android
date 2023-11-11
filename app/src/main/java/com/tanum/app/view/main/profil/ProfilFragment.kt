package com.tanum.app.view.main.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tanum.app.databinding.FragmentProfilBinding
import com.tanum.app.view.login.LoginActivity
import com.tanum.app.viewmodels.ProfilViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val profilViewModel: ProfilViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        factory = ViewModelFactory.getInstance(requireActivity())
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.buttonLogout.setOnClickListener {
            profilViewModel.logout()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setupView() {
        lifecycleScope.launch {
            profilViewModel.profile.collect { profile ->
                binding.apply {
                    tvNamaLengkapUser.text = profile.fullName
                    tvEmailUser.text = profile.email
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}