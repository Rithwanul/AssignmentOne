package com.example.assignmentone.model.response.character

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class WandResponse(
	@SerializedName("wood") var wood   : String? = null,
	@SerializedName("core") var core   : String? = null,
	@SerializedName("length" ) var length : Double?    = null
) : Parcelable