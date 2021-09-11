package com.w4eret1ckrtb1tch.app34.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("data")
    val user: User,
    @SerializedName("support")
    val support: Support
)
