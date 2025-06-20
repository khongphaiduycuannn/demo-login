package com.duycuannn.demologin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycuannn.demologin.data.repository.AuthRepository
import com.duycuannn.demologin.data.repository.DataState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState> = _uiState


    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (!validateUsername(username)) {
                _uiState.value = LoginUiState.Error("Vui lòng nhập tên đăng nhập!")
                return@launch
            }

            if (!validatePassword(password)) {
                _uiState.value = LoginUiState.Error("Vui lòng nhập mật khẩu!")
                return@launch
            }

            _uiState.value = LoginUiState.Loading
            when (val response = authRepository.login(username, password)) {
                is DataState.Error -> {
                    _uiState.value = LoginUiState.Error("Đăng nhập thất bại!")
                }

                is DataState.Success -> {
                    _uiState.value = LoginUiState.Success(response.data)
                }
            }
        }
    }

    private fun validateUsername(username: String): Boolean {
        return username.isNotBlank()
    }

    private fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }
}