package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.LahanData
import com.tanum.app.model.data.Meta

data class LahanResponse(

	@field:SerializedName("data")
	val data: LahanData,

	@field:SerializedName("meta")
	val meta: Meta
)