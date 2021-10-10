package com.swati.loginapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.swati.loginapplication.vm.LoginUiState
import com.swati.loginapplication.vm.LoginViewModel
import com.swati.loginapplication.databinding.ActivityLoginBinding
import com.swati.loginapplication.utils.Constants
import com.swati.loginapplication.utils.onChange
import kotlinx.coroutines.flow.collect

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextWatchers()
        setListener()
        observeEvents()
    }

    private fun setTextWatchers() {
        binding.etUsername.onChange {
            viewModel.onUserNameTextChanged(
                binding.etUsername.text.toString(), binding.etPassword.text.toString()
            )
        }

        binding.etPassword.onChange {
            viewModel.onPasswordTextChanged(
                binding.etUsername.text.toString(), binding.etPassword.text.toString()
            )
        }
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.onLoginBtnClick()
        }
    }

    private fun observeEvents() {
        viewModel.showSnackBar.observe(this, { errorTextResId ->
            setErrorVisibility(View.VISIBLE)
            binding.tvError.setText(errorTextResId)
        })

        viewModel.loginUiBtnState.observe(this, { btnEnabled ->
            setLoginBtnState(btnEnabled)
        })

        viewModel.loginUiErrorState.observe(this, { errorVisibility ->
            setErrorVisibility(errorVisibility)
        })

        lifecycleScope.launchWhenStarted {
            viewModel.loginUiState.collect { loginUiState ->
                setLoginUiState(loginUiState)
            }
        }
    }

    private fun setErrorVisibility(errorVisible: Int) {
        binding.tvError.visibility = errorVisible
    }

    private fun setLoginBtnState(loginBtnEnabled: Boolean) {
        binding.btnLogin.isEnabled = loginBtnEnabled
    }

    private fun setLoginUiState(loginUiState: LoginUiState) {
        when (loginUiState) {
            is LoginUiState.Success -> {
                onSuccessfulLogin()
            }

            is LoginUiState.Loading -> {
                setProgressVisibility(true)
                setLoginBtnState(false)
            }
            else -> Unit
        }
    }

    private fun onSuccessfulLogin() {
        redirectToMainScreen()
        setProgressVisibility(false)
    }

    private fun redirectToMainScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(Constants.NAME, binding.etUsername.text.toString())
        startActivity(intent)
        finish()
    }

    private fun setProgressVisibility(progressVisible: Boolean) {
        binding.progressBar.isVisible = progressVisible
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
