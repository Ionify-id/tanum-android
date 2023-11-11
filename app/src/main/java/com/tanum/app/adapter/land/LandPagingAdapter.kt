package com.tanum.app.adapter.land

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanum.app.model.data.LahanData
import com.tanum.app.databinding.ItemLandBinding
import com.tanum.app.utils.calculateAge
import com.tanum.app.utils.getImage

class LandPagingAdapter: PagingDataAdapter<LahanData, LandPagingAdapter.LandPagingViewHolder>( DIFF_CALLBACK ) {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class LandPagingViewHolder(
        private val binding: ItemLandBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LahanData) {
            val tanaman = "Tanaman: ${data.plant}"
            val varietas = "Varietas: ${data.varietas}"
            val luas = "Luas: ${data.area}m²"
            val usia = "Usia: ${calculateAge(data.dateStart)}"
            with(binding) {
                tvNamaLahan.text = data.name
                tvNamaKomoditas.text = tanaman
                tvNamaVarietas.text = varietas
                tvLuas.text = luas
                tvUsia.text= usia
                ivItemLahan.setImageResource(getImage(data.image))
            }
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(data)
            }
        }
    }

    override fun onBindViewHolder(holder: LandPagingAdapter.LandPagingViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LandPagingAdapter.LandPagingViewHolder {
        val binding = ItemLandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LandPagingViewHolder(binding)
    }

    interface OnItemClickCallback {
        fun onItemClicked(land: LahanData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LahanData>() {
            override fun areItemsTheSame(oldItem: LahanData, newItem: LahanData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LahanData, newItem: LahanData): Boolean {
                return oldItem == newItem
            }
        }
    }
}