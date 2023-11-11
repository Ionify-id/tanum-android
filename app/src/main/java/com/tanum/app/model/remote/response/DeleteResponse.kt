package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.Meta

data class DeleteResponse(

	@field:SerializedName("data")
	val data: DeleteMessage,

	@field:SerializedName("meta")
	val meta: Meta
)

data class DeleteMessage(
	@field:SerializedName("message")
	val message: String
)
