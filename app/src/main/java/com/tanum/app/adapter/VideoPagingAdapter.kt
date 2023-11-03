package com.tanum.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanum.app.data.model.VideoData
import com.tanum.app.databinding.ItemVideoBinding

class VideoPagingAdapter: PagingDataAdapter<VideoData, VideoPagingAdapter.VideoPagingViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class VideoPagingViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPagingViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoPagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoPagingViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) holder.bind(data)
    }

    private fun extractVideoId(youtubeUrl: String): String {
        val pattern = "v=([^&]+)".toRegex()
        val matchResult = pattern.find(youtubeUrl)
        return matchResult?.groups?.get(1)?.value ?: ""
    }

    interface OnItemClickCallback {
        fun onItemClicked(video: VideoData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoData>() {
            override fun areItemsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.title == newItem.title &&
                        oldItem.url == newItem.url
            }
        }
    }
}