package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.Meta
import com.tanum.app.data.model.VideoData

data class ListVideoResponse(

	@field:SerializedName("data")
	val data: List<VideoData?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)


