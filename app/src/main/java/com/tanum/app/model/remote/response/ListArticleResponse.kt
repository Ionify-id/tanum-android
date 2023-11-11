package com.tanum.app.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.Meta
import kotlinx.parcelize.Parcelize

data class ListArtikelResponse(

	@field:SerializedName("data")
	val data: ArrayList<ArticleListItem>,

	@field:SerializedName("meta")
	val meta: Meta
)

@Parcelize
data class ArticleListItem(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("title")
	val title: String
): Parcelable
