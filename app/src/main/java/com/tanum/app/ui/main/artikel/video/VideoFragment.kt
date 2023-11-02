package com.tanum.app.ui.main.artikel.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tanum.app.R
import com.tanum.app.databinding.FragmentBeritaBinding
import com.tanum.app.databinding.FragmentVideoBinding
import com.tanum.app.ui.main.artikel.berita.berita_detail.BeritaDetailFragment
import com.tanum.app.ui.main.artikel.video.video_detail.VideoDetailFragment

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val link = "https://www.youtube.com/watch?v=rJVYb2Ib8DU"
        val videoId = extractVideoId(link)
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/default.jpg"
        Glide.with(this).load(thumbnailUrl).into(binding.thumbnail)
        binding.thumbnail.setOnClickListener {
            val videoDetailFragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putString(VideoDetailFragment.EXTRA_LINK, link)
            videoDetailFragment.arguments = bundle

            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerVideo, videoDetailFragment, VideoDetailFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        return root
    }

    private fun extractVideoId(youtubeUrl: String): String {
        val pattern = "v=([^&]+)".toRegex()
        val matchResult = pattern.find(youtubeUrl)
        return matchResult?.groups?.get(1)?.value ?: ""
    }
}