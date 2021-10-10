package com.swati.loginapplication.vm

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swati.loginapplication.R
import com.swati.loginapplication.utils.Constants
import com.swati.loginapplication.utils.SingleLiveEvent
import com.swati.loginapplication.utils.Validator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    val showSnackBar = SingleLiveEvent<Int>()
    val loginUiBtnState =
        SingleLiveEvent<Boolean>()
    val loginUiErrorState =
        SingleLiveEvent<Int>()

    fun onLoginBtnClick() = viewModelScope.launch {
        _loginUiState.value = LoginUiState.Loading
        delay(Constants.DELAY)
        _loginUiState.value = LoginUiState.Success
    }

    fun onUserNameTextChanged(userNameText: String, passwordText: String) {
        val validUserName = Validator.isValidUserName(userNameText)
        val validPassword = Validator.isValidPassword(passwordText)
        viewModelScope.launch {
            if (userNameText.isNotEmpty() && validUserName) {
                if (passwordText.isNotEmpty() && validPassword) {
                    loginUiBtnState.value = true
                    loginUiErrorState.value = View.GONE
                } else {
                    loginUiErrorState.value = View.GONE
                    loginUiBtnState.value = false
                }
            } else {
                if (userNameText.isEmpty()) {
                    loginUiErrorState.value = View.GONE
                } else {
                    showSnackBar.value = R.string.enter_proper_user_name
                    loginUiErrorState.value = View.VISIBLE
                }
                loginUiBtnState.value = false
            }
        }

    }

    fun onPasswordTextChanged(userNameText: String, passwordText: String) {
        val validUserName = Validator.isValidUserName(userNameText)
        val validPassword = Validator.isValidPassword(passwordText)

        viewModelScope.launch {

            if (passwordText.isNotEmpty() && validPassword) {
                if (userNameText.isNotEmpty() && validUserName) {
                    loginUiBtnState.value = true
                    loginUiErrorState.value = View.GONE
                } else {
                    showSnackBar.value = R.string.enter_proper_user_name
                    loginUiErrorState.value = View.VISIBLE
                    loginUiBtnState.value = false
                }
            } else {
                if (passwordText.isEmpty()) {
                    loginUiErrorState.value = View.GONE
                } else {
                    showSnackBar.value = R.string.password_mismatch
                    loginUiErrorState.value = View.VISIBLE
                }
                loginUiBtnState.value = false
            }
        }

    }
}