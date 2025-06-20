package com.duycuannn.demologin.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.duycuannn.demologin.data.repository.AuthRepository
import com.duycuannn.demologin.data.source.network.AuthService
import com.duycuannn.demologin.databinding.ActivityLoginBinding
import com.duycuannn.demologin.utils.constants.ApiConstants
import com.duycuannn.demologin.utils.constants.AuthPrefsConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<LoginViewModel> {
        val authRepository = AuthRepository(
            authService = createAuthService(),
            prefs = createPreferences()
        )
        LoginViewModelFactory(authRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        handleEvents()
        observeData()
    }


    private fun handleEvents() {
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.login(username, password)
        }
    }


    private fun observeData() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                LoginUiState.Idle -> {

                }

                LoginUiState.Loading -> {
                    binding.btnLogin.isEnabled = false
                    binding.tvMessage.text = "Thông báo: Đang đăng nhập..."
                    binding.tvUserInfo.text = ""
                }

                is LoginUiState.Error -> {
                    binding.btnLogin.isEnabled = true
                    binding.tvMessage.text = "Thông báo: ${state.message}"
                    binding.tvUserInfo.text = ""
                }

                is LoginUiState.Success -> {
                    binding.btnLogin.isEnabled = true
                    binding.tvMessage.text = "Thông báo: Đăng nhập thành công!"
                    binding.tvUserInfo.text = "Thông tin user: ${state.user}"

                    val token = getSharedPreferences("auth", MODE_PRIVATE)
                        .getString(AuthPrefsConstants.ACCESS_TOKEN, "")
                    Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun createAuthService(): AuthService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    private fun createPreferences(): SharedPreferences {
        return getSharedPreferences("auth", MODE_PRIVATE)
    }
}