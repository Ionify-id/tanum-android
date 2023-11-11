package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.Meta
import com.tanum.app.model.data.VideoData

data class ListVideoResponse(

	@field:SerializedName("data")
	val data: ArrayList<VideoData>,

	@field:SerializedName("meta")
	val meta: Meta
)


