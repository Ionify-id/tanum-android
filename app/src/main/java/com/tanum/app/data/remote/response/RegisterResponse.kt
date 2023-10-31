package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: FullNameData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class FullNameData(

	@field:SerializedName("fullName")
	val fullName: String
)
