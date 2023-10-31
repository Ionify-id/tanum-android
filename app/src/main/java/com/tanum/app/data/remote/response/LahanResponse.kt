package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.LahanData
import com.tanum.app.data.model.Meta

data class LahanResponse(

	@field:SerializedName("data")
	val data: LahanData? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)
