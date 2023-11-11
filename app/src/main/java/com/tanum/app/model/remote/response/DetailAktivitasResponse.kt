package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.Meta

data class DetailAktivitasResponse(

	@field:SerializedName("data")
	val data: ActivityData,

	@field:SerializedName("meta")
	val meta: Meta
)

data class ActivityData(

	@field:SerializedName("activity")
	val activity: Activity
)

data class Activity(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("landId")
	val landId: Int,

	@field:SerializedName("cost")
	val cost: Int,

	@field:SerializedName("dateAction")
	val dateAction: String,

	@field:SerializedName("action")
	val action: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
