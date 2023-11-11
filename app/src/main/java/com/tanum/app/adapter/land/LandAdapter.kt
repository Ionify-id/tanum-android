package com.tanum.app.adapter.land

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanum.app.model.data.LahanData
import com.tanum.app.databinding.ItemLandBinding
import com.tanum.app.utils.LandDiffCallback
import com.tanum.app.utils.calculateAge
import com.tanum.app.utils.getImage

class LandAdapter: RecyclerView.Adapter<LandAdapter.LandViewHolder>() {

    private val listLand = ArrayList<LahanData>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class LandViewHolder(private val binding: ItemLandBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LahanData) {
            binding.apply {
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandViewHolder {
        val binding = ItemLandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LandViewHolder(binding)
    }

    override fun getItemCount(): Int = this.listLand.size

    override fun onBindViewHolder(holder: LandViewHolder, position: Int) {
        holder.bind(listLand[position])
    }

    fun setListLand(listLand: ArrayList<LahanData>) {
        val diffCallback = LandDiffCallback(this.listLand, listLand)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listLand.clear()
        this.listLand.addAll(listLand)
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(land: LahanData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}