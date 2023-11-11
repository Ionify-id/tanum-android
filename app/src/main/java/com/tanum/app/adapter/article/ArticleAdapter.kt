package com.tanum.app.adapter.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanum.app.model.remote.response.ArticleListItem
import com.tanum.app.databinding.ItemArticleBinding
import com.tanum.app.utils.ArticleDiffCallback

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val listArticle = ArrayList<ArticleListItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = this.listArticle.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }

    fun setListArticle(listArticle: ArrayList<ArticleListItem>) {
        val diffCallback = ArticleDiffCallback(this.listArticle, listArticle)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listArticle.clear()
        this.listArticle.addAll(listArticle)
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(article: ArticleListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}