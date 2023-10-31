package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.model.Meta

data class ListLahanResponse(

	@field:SerializedName("data")
	val data: List<LahanData?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)
