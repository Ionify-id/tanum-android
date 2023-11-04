package com.tanum.app.adapter.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanum.app.data.remote.response.ArticleListItem
import com.tanum.app.databinding.ItemArticleBinding

class ArticlePagingAdapter: PagingDataAdapter<ArticleListItem, ArticlePagingAdapter.ArticlePagingViewHolder>(
    DIFF_CALLBACK
) {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ArticlePagingViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articleListItem: ArticleListItem) {
            binding.apply {
                tvJudulBerita.text = articleListItem.title
                Glide.with(itemView.context)
                    .load(articleListItem.thumbnail)
                    .into(ivItemBerita)
            }
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(articleListItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlePagingViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticlePagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlePagingViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(article: ArticleListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleListItem>() {
            override fun areItemsTheSame(oldItem: ArticleListItem, newItem: ArticleListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleListItem, newItem: ArticleListItem): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.title == newItem.title &&
                        oldItem.thumbnail == newItem.thumbnail
            }
        }
    }
}