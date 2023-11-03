package com.tanum.app.ui.main.artikel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanum.app.R
import com.tanum.app.adapter.ArticleAdapter
import com.tanum.app.adapter.VideoAdapter
import com.tanum.app.data.model.VideoData
import com.tanum.app.data.remote.response.ArticleListItem
import com.tanum.app.databinding.FragmentArtikelBinding
import com.tanum.app.ui.main.artikel.berita.BeritaActivity
import com.tanum.app.ui.main.artikel.berita.berita_detail.BeritaDetailActivity
import com.tanum.app.ui.main.artikel.video.VideoActivity
import com.tanum.app.ui.main.artikel.video.video_detail.VideoDetailActivity
import com.tanum.app.utils.Result
import com.tanum.app.viewmodels.ArtikelViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

class ArtikelFragment : Fragment() {

    private var _binding: FragmentArtikelBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val artikelViewModel: ArtikelViewModel by viewModels { factory }
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        factory = ViewModelFactory.getInstance(requireActivity())
        _binding = FragmentArtikelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter()
        videoAdapter = VideoAdapter()
        setupView()
        setupAction()
    }

    private fun setupView() {
        lifecycleScope.launch {
            artikelViewModel.token.collect { token ->
                observeArticleData(token)
                observeVideoData(token)
            }
        }
    }

    private fun setupAction() {
        binding.tvBeritaLainnya.setOnClickListener {
            val intent = Intent(activity, BeritaActivity::class.java)
            startActivity(intent)
        }
        binding.tvVideoLainnya.setOnClickListener {
            val intent = Intent(activity, VideoActivity::class.java)
            startActivity(intent)
        }
        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(article: ArticleListItem) {
                val intentToDetail = Intent(requireActivity(), BeritaDetailActivity::class.java)
                intentToDetail.putExtra(BeritaDetailActivity.EXTRA_ID, article.id)
                startActivity(intentToDetail)
            }
        })
        videoAdapter.setOnItemClickCallback(object : VideoAdapter.OnItemClickCallback {
            override fun onItemClicked(article: VideoData) {
                val intentToDetail = Intent(requireActivity(), VideoDetailActivity::class.java)
                intentToDetail.putExtra(VideoDetailActivity.EXTRA_URL, article.url)
                startActivity(intentToDetail)
            }
        })
    }

    private fun observeArticleData(token: String) {
        artikelViewModel.getThreeArticles(token).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        with (binding) {
                            handleLoadingState(
                                progressBarArtikel,
                                true,
                                tvBeritaNullInfo,
                                rvListBerita
                            )
                        }
                    }
                    is Result.Success -> {
                        with (binding) {
                            handleLoadingState(
                                progressBarArtikel,
                                false,
                                tvBeritaNullInfo,
                                rvListBerita
                            )
                            handleSuccessState(
                                result.data,
                                rvListBerita,
                                articleAdapter
                            )
                        }

                    }
                    is Result.Error -> {
                        with (binding) {
                            handleLoadingState(
                                progressBarArtikel,
                                false,
                                tvBeritaNullInfo,
                                rvListBerita
                            )
                            handleErrorState(
                                tvBeritaNullInfo,
                                getString(R.string.failed_news)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeVideoData(token: String) {
        artikelViewModel.getThreeVideos(token).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        with (binding) {
                            handleLoadingState(
                                progressBarVideo,
                                true,
                                tvVideoNullInfo,
                                rvListVideo
                            )
                        }

                    }
                    is Result.Success -> {
                        with (binding) {
                            handleLoadingState(
                                progressBarVideo,
                                false,
                                tvVideoNullInfo,
                                rvListVideo
                            )
                            handleSuccessState(
                                result.data,
                                rvListVideo,
                                videoAdapter
                            )
                        }

                    }
                    is Result.Error -> {
                        with (binding) {
                            handleLoadingState(
                                progressBarVideo,
                                false,
                                tvVideoNullInfo,
                                rvListVideo
                            )
                            handleErrorState(
                                tvVideoNullInfo,
                                getString(R.string.failed_video)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleLoadingState(
        progressBar: ProgressBar,
        isLoading: Boolean,
        textView: TextView,
        recyclerView: RecyclerView
    ) {
        showLoading(progressBar, isLoading)
        showRecyclerView(textView, recyclerView)
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
            is VideoAdapter -> {
                adapter.setListVideo(data as ArrayList<VideoData>)
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

    private fun showRecyclerView(textView: TextView, recyclerView: RecyclerView) {
        textView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}