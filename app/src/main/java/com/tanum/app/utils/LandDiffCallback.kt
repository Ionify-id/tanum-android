package com.tanum.app.utils

import androidx.recyclerview.widget.DiffUtil
import com.tanum.app.model.data.LahanData

class LandDiffCallback(
    private val oldLandList: ArrayList<LahanData>,
    private val newLandList: ArrayList<LahanData>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldLandList.size

    override fun getNewListSize(): Int = newLandList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldLandList[oldItemPosition] == newLandList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldLandList[oldItemPosition].id == newLandList[newItemPosition].id
    }
}