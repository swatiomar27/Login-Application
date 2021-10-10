package com.swati.loginapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swati.loginapplication.R
import com.swati.loginapplication.databinding.ActivityHomeBinding
import com.swati.loginapplication.utils.Constants

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(Constants.NAME)
        binding.tvUsername.text = String.format(getString(R.string.hello), name)
    }
}