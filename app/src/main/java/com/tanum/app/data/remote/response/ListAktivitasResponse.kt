package com.tanum.app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.data.model.AktivitasData
import com.tanum.app.data.model.Meta

data class ListAktivitasResponse(

	@field:SerializedName("data")
	val data: ListActivitiesData,

	@field:SerializedName("meta")
	val meta: Meta
)

data class ListActivitiesData(

	@field:SerializedName("activities")
	val activities: ArrayList<AktivitasData>
)
