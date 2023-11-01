package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.Meta

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

