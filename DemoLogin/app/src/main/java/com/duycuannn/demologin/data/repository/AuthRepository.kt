package com.duycuannn.demologin.data.repository

import android.content.SharedPreferences
import com.duycuannn.demologin.data.model.User
import com.duycuannn.demologin.data.model.network.LoginRequest
import com.duycuannn.demologin.data.source.network.AuthService
import com.duycuannn.demologin.utils.constants.AuthPrefsConstants

class AuthRepository(
    private val authService: AuthService,
    private val prefs: SharedPreferences
) {

    suspend fun login(username: String, password: String): DataState<User> {
        try {
            val response = authService.login(LoginRequest(username, password))
            val accessToken = response.data.token
            prefs.edit().putString(AuthPrefsConstants.ACCESS_TOKEN, accessToken).apply()

            return DataState.Success(response.data.user)
        } catch (ex: Exception) {
            return DataState.Error(ex)
        }
    }
}