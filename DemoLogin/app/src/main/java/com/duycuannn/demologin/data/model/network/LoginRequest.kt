package com.duycuannn.demologin.data.model.network

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    val password: String
)