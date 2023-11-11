package com.tanum.app.utils

import androidx.recyclerview.widget.DiffUtil
import com.tanum.app.model.data.VideoData

class VideoDiffCallback(
    private val oldVideoList: ArrayList<VideoData>,
    private val newVideoList: ArrayList<VideoData>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldVideoList.size

    override fun getNewListSize(): Int = newVideoList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldVideoList[oldItemPosition] == newVideoList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldVideoList[oldItemPosition]
        val newVideo = newVideoList[newItemPosition]
        return (
                oldVideo.id == newVideo.id &&
                oldVideo.title == newVideo.title &&
                oldVideo.url == newVideo.url &&
                oldVideo.createdAt == newVideo.createdAt &&
                oldVideo.updatedAt == newVideo.updatedAt
                )
    }
}