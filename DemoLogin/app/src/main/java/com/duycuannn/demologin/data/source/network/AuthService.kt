package com.duycuannn.demologin.data.source.network

import com.duycuannn.demologin.data.model.network.ApiResponse
import com.duycuannn.demologin.data.model.network.LoginRequest
import com.duycuannn.demologin.data.model.network.LoginResponse
import com.duycuannn.demologin.utils.constants.ApiConstants
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST(ApiConstants.EndPoint.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ) : ApiResponse<LoginResponse>
}