package com.swati.loginapplication.vm

sealed class LoginUiState {
    object Success : LoginUiState()
    object Loading : LoginUiState()
    object Empty : LoginUiState()
}
