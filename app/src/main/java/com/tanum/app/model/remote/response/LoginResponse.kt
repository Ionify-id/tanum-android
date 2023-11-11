package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.Meta

data class LoginResponse(

	@field:SerializedName("data")
	val data: TokenData,

	@field:SerializedName("meta")
	val meta: Meta
)

data class TokenData(

	@field:SerializedName("token")
	val token: String
)
