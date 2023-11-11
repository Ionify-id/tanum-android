package com.tanum.app.model.remote.response

import com.google.gson.annotations.SerializedName
import com.tanum.app.model.data.LahanData
import com.tanum.app.model.data.Meta

data class DetailLahanResponse(

	@field:SerializedName("data")
	val data: LahanData,

	@field:SerializedName("meta")
	val meta: Meta
)

data class DetailLahanData(

	@field:SerializedName("area")
	val area: Int,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("varietas")
	val varietas: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("ownership")
	val ownership: String,

	@field:SerializedName("dateStart")
	val dateStart: String,

	@field:SerializedName("plant")
	val plant: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("profit")
	val profit: Int,

	@field:SerializedName("totalCost")
	val totalCost: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
