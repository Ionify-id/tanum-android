package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: FullNameData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class FullNameData(

	@field:SerializedName("fullName")
	val fullName: String? = null
)
