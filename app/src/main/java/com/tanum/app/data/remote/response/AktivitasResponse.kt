package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.AktivitasData
import com.tanum.app.data.model.Meta

data class AktivitasResponse(

	@field:SerializedName("data")
	val data: AktivitasData? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)