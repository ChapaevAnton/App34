package com.w4eret1ckrtb1tch.app34.data.dto.user

import com.google.gson.annotations.SerializedName

data class Support(
    @SerializedName("url")
    val url: String,
    @SerializedName("text")
    val text: String
)
