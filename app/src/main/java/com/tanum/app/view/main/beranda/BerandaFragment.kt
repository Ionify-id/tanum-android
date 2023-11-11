package com.tanum.app.view.main.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tanum.app.R
import com.tanum.app.adapter.article.ArticleAdapter
import com.tanum.app.adapter.land.LandAdapter
import com.tanum.app.model.data.LahanData
import com.tanum.app.model.remote.response.ArticleListItem
import com.tanum.app.databinding.FragmentBerandaBinding
import com.tanum.app.view.main.artikel.berita.berita_detail.BeritaDetailActivity
import com.tanum.app.view.main.lahan_saya.detail_lahan.aktivitas.DetailLahanActivity
import com.tanum.app.utils.Result
import com.tanum.app.viewmodels.BerandaViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val berandaViewModel: BerandaViewModel by viewModels { factory }
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var landAdapter: LandAdapter
    private var listLand = ArrayList<LahanData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        factory = ViewModelFactory.getInstance(requireActivity())
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter()
        landAdapter = LandAdapter()
        setupView()
        setupAction()
    }

    private fun setupAction() {
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)

        binding.tvLihatLahanLainnya.setOnClickListener {
            bottomNav?.selectedItemId = R.id.navigation_lahan_saya
        }

        binding.tvArtikelLainnya.setOnClickListener {
            bottomNav?.selectedItemId = R.id.navigation_artikel
        }

        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: ArticleListItem) {
                val intentToDetail = Intent(requireActivity(), BeritaDetailActivity::class.java)
                intentToDetail.putExtra(BeritaDetailActivity.EXTRA_ID, article.id)
                startActivity(intentToDetail)
            }
        })

        landAdapter.setOnItemClickCallback(object : LandAdapter.OnItemClickCallback {
            override fun onItemClicked(land: LahanData) {
                val intentToDetail = Intent(requireActivity(), DetailLahanActivity::class.java)
                intentToDetail.putExtra(DetailLahanActivity.EXTRA_LAND_ID, land.id)
                startActivity(intentToDetail)
            }

        })
    }

    private fun setupView() {
        lifecycleScope.launch {
            setHtmlText()
            berandaViewModel.token.collect { token ->
                observeArticleData(token)
                observeLandData(token)
            }
        }
    }

    private fun observeArticleData(token: String) {
        if (view != null) {
            berandaViewModel.getThreeArticles(token).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            with(binding) {
                                handleLoadingState(
                                    progressBarArtikel,
                                    true,
                                    tvArtikelNullInfo,
                                    recyclerViewArtikel
                                )
                            }
                        }

                        is Result.Success -> {
                            with(binding) {
                                handleLoadingState(
                                    progressBarArtikel,
                                    false,
                                    tvArtikelNullInfo,
                                    recyclerViewArtikel
                                )
                                if (result.data.isEmpty()) {
                                    showRecyclerView(
                                        tvArtikelNullInfo,
                                        recyclerViewArtikel,
                                        false
                                    )
                                } else {
                                    handleSuccessState(
                                        result.data,
                                        recyclerViewArtikel,
                                        articleAdapter
                                    )
                                }
                            }

                        }

                        is Result.Error -> {
                            with(binding) {
                                handleLoadingState(
                                    progressBarArtikel,
                                    false,
                                    tvArtikelNullInfo,
                                    recyclerViewArtikel
                                )
                                handleErrorState(
                                    tvArtikelNullInfo,
                                    getString(R.string.failed_news)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeLandData(token: String) {
        if (view != null) {
            berandaViewModel.getThreeLands(token).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            with(binding) {
                                handleLoadingState(
                                    progressBarLahanSaya,
                                    true,
                                    tvLahanSayaNullInfo,
                                    recyclerViewLahanSaya
                                )
                            }
                        }

                        is Result.Error -> {
                            with(binding) {
                                handleLoadingState(
                                    progressBarLahanSaya,
                                    false,
                                    tvLahanSayaNullInfo,
                                    recyclerViewLahanSaya
                                )
                                handleErrorState(
                                    tvLahanSayaNullInfo,
                                    getString(R.string.failed_to_load_lands)
                                )
                            }
                        }

                        is Result.Success -> {
                            with(binding) {
                                handleLoadingState(
                                    progressBarLahanSaya,
                                    false,
                                    tvLahanSayaNullInfo,
                                    recyclerViewLahanSaya
                                )
                                if (result.data.isEmpty()) {
                                    showRecyclerView(
                                        tvLahanSayaNullInfo,
                                        recyclerViewLahanSaya,
                                        false
                                    )
                                    listLand = result.data
                                } else {
                                    handleSuccessState(
                                        result.data,
                                        recyclerViewLahanSaya,
                                        landAdapter
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun setHtmlText() {
        val welcomeTextHtml = getString(R.string.welcome_message_html)
        val styledText = HtmlCompat.fromHtml(welcomeTextHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvWelcomeBeranda.text = styledText
    }

    private fun handleLoadingState(
        progressBar: ProgressBar,
        isLoading: Boolean,
        textView: TextView,
        recyclerView: RecyclerView
    ) {
        showLoading(progressBar, isLoading)
        showRecyclerView(textView, recyclerView, true)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> handleSuccessState(
        data: ArrayList<T>,
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>
    ) {
        when (adapter) {
            is ArticleAdapter -> {
                adapter.setListArticle(data as ArrayList<ArticleListItem>)
            }
            is LandAdapter -> {
                adapter.setListLand(data as ArrayList<LahanData>)
            }
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun handleErrorState(textView: TextView, message: String) {
        textView.text = message
    }

    private fun showLoading(progressBar: ProgressBar, isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerView(textView: TextView, recyclerView: RecyclerView, isShow: Boolean) {
        if (isShow) {
            textView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        refreshLahanList()
    }

    private fun refreshLahanList() {
        lifecycleScope.launch {
            berandaViewModel.token.collect { token ->
                observeLandData(token)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}