package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.ArtikelData
import com.tanum.app.data.model.Meta

data class ArtikelResponse(

	@field:SerializedName("data")
	val data: ArtikelData? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)
