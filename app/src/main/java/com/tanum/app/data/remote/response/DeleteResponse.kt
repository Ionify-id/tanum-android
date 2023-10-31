package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.Meta

data class DeleteResponse(

	@field:SerializedName("data")
	val data: DeleteMessage? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class DeleteMessage(

	@field:SerializedName("message")
	val message: String? = null
)
