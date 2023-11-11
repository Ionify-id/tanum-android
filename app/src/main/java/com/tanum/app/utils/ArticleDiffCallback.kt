package com.tanum.app.utils

import androidx.recyclerview.widget.DiffUtil
import com.tanum.app.model.remote.response.ArticleListItem

class ArticleDiffCallback(
    private val oldArticleList: ArrayList<ArticleListItem>,
    private val newArticleList: ArrayList<ArticleListItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldArticleList.size

    override fun getNewListSize(): Int = newArticleList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldArticleList[oldItemPosition] == newArticleList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldArticle = oldArticleList[oldItemPosition]
        val newArticle = newArticleList[newItemPosition]
        return (
                oldArticle.id == newArticle.id &&
                oldArticle.title == newArticle.title &&
                oldArticle.thumbnail == newArticle.thumbnail
                )
    }

}