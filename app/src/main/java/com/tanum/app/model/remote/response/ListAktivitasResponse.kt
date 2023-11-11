package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.AktivitasData
import com.tanum.app.model.data.Meta

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
