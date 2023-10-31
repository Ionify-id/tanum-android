package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.ProfileData

data class ProfileResponse(

	@field:SerializedName("data")
	val data: ProfileData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
