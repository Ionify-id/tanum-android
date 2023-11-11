package com.tanum.app.adapter.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanum.app.data.model.AktivitasData
import com.tanum.app.data.model.LahanData
import com.tanum.app.databinding.ItemActivityBinding
import com.tanum.app.utils.DateFormatter

class ActivityPagingAdapter: PagingDataAdapter<AktivitasData, ActivityPagingAdapter.ActivityViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ActivityViewHolder(private val binding: ItemActivityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AktivitasData) {
            val cost = "Rp ${data.cost}"
            binding.apply {
                tvTanggal.text = DateFormatter.formatToFullDateFormat(data.dateAction)
                tvKategoriInfo.text = data.category
                tvKegiatanInfo.text = data.action
                tvBiayaInfo.text = cost
                buttonEdit.setOnClickListener { onItemClickCallback?.onItemEditClick(data.id) }
                buttonDelete.setOnClickListener { onItemClickCallback?.onItemDeleteClick(data.id) }
            }
        }

    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    interface OnItemClickCallback {
        fun onItemEditClick(activityId: Int)
        fun onItemDeleteClick(activityId: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AktivitasData>() {
            override fun areItemsTheSame(oldItem: AktivitasData, newItem: AktivitasData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AktivitasData, newItem: AktivitasData): Boolean {
                return oldItem == newItem
            }
        }
    }
}