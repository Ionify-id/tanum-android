package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.AktivitasData
import com.tanum.app.model.data.Meta

data class AktivitasResponse(

	@field:SerializedName("data")
	val data: AktivitasData,

	@field:SerializedName("meta")
	val meta: Meta
)