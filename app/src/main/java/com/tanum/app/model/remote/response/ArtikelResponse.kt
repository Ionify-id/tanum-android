package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.ArtikelData
import com.tanum.app.model.data.Meta

data class ArtikelResponse(

	@field:SerializedName("data")
	val data: ArtikelData,

	@field:SerializedName("meta")
	val meta: Meta
)
