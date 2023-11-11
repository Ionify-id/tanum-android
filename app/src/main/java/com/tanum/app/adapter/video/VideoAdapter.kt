package com.tanum.app.adapter.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanum.app.model.data.VideoData
import com.tanum.app.databinding.ItemVideoBinding
import com.tanum.app.utils.VideoDiffCallback

class VideoAdapter: RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private val listVideo = ArrayList<VideoData>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(videoData: VideoData) {
            binding.apply {
                tvJudulVideo.text = videoData.title
                val videoId = extractVideoId(videoData.url)
                val thumbnailUrl = "https://img.youtube.com/vi/$videoId/default.jpg"
                Glide.with(itemView.context)
                    .load(thumbnailUrl)
                    .into(ivItemVideo)
            }
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(videoData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int = this.listVideo.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }

    private fun extractVideoId(youtubeUrl: String): String {
        val pattern = "v=([^&]+)".toRegex()
        val matchResult = pattern.find(youtubeUrl)
        return matchResult?.groups?.get(1)?.value ?: ""
    }

    fun setListVideo(listVideo: ArrayList<VideoData>) {
        val diffCallback = VideoDiffCallback(this.listVideo, listVideo)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listVideo.clear()
        this.listVideo.addAll(listVideo)
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(article: VideoData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}