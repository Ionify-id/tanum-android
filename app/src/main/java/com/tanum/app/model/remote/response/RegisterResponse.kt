package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.Meta

data class RegisterResponse(
	@field:SerializedName("data")
	val data: Data,
	@field:SerializedName("meta")
	val meta: Meta
)

data class Data(
	@field:SerializedName("fullName")
	val fullName: String
)

