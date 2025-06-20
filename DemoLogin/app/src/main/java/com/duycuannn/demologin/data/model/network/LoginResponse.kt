package com.duycuannn.demologin.data.model.network

import com.duycuannn.demologin.data.model.User

data class LoginResponse(
    val token: String,
    val user: User
)