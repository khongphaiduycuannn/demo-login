package com.duycuannn.demologin.data.model.network

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)