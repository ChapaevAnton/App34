package com.w4eret1ckrtb1tch.app34.domain

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val user: User,
    @SerializedName("support")
    val support: Support
)
