package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.Meta

data class ListArtikelResponse(

	@field:SerializedName("data")
	val data: List<ArtikelListItem?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class ArtikelListItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
