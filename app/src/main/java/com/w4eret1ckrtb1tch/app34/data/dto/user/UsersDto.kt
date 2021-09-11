package com.w4eret1ckrtb1tch.app34.data.dto.user

import com.google.gson.annotations.SerializedName

//"page": 2,
//"per_page": 6,
//"total": 12,
//"total_pages": 2,
//"data":[]
//"support":

data class UsersDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPage: Int,
    @SerializedName("data")
    val users: List<User>,
    @SerializedName("support")
    val support: Support
)