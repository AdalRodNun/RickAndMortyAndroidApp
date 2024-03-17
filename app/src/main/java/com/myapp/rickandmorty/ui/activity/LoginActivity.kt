package com.myapp.rickandmorty.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ActivityLoginBinding
import com.myapp.rickandmorty.ui.fragment.RegisterFragment
import com.myapp.rickandmorty.ui.fragment.RegisterFragment.Companion.TAG
import com.myapp.rickandmorty.ui.viewModel.LoginViewModel
import com.myapp.rickandmorty.utils.ExtendedFunctions.addSimpleTextChangedListener
import com.myapp.rickandmorty.utils.ExtendedFunctions.getPackageInfoCompat
import com.myapp.rickandmorty.utils.ExtendedFunctions.goActivity
import com.myapp.rickandmorty.utils.Functions.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        manageLottieTheme()
        setListeners()
        setVersion()
        getObservers()
    }

    private fun setVersion() {
        val packageInfo = packageManager.getPackageInfoCompat(packageName)
        binding.tvVersion.text = getString(R.string.version_param, packageInfo.versionName)
    }

    private fun setListeners() = with(binding) {
        btLogin.setOnClickListener {
            if (validateInputs()) {
                checkLogin()
            }
        }

        btRegister.setOnClickListener {
            RegisterFragment().show(supportFragmentManager, TAG)
        }

        etEmail.addSimpleTextChangedListener {
            tilEmail.error = null
        }

        etPassword.addSimpleTextChangedListener {
            tilPassword.error = null
        }
    }

    private fun getObservers() = with(viewModel) {
        checked.observe(this@LoginActivity) {
            if (it) {
                goActivity(activity = HomeActivity(), finishCurrent = true)
            } else {
                binding.tilPassword.error = getString(R.string.wrong_password)
            }
        }
    }

    private fun validateInputs(): Boolean = with(binding) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        var valid = true

        if (email.isEmpty()) {
            tilEmail.error = getString(R.string.empty_email_error)
            valid = false

        } else if (isValidEmail(email)) {
            tilEmail.error = getString(R.string.invalid_email_error)
            valid = false
        }

        if (password.isEmpty()) {
            tilPassword.error = getString(R.string.empty_password_error)
            valid = false
        }

        return valid
    }

    private fun checkLogin() {
        viewModel.checkUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    private fun manageLottieTheme() {
        val animationName =
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> "android_andy_negative.json"
                Configuration.UI_MODE_NIGHT_NO -> "android_andy.json"
                else -> "android_andy.json"
            }

        binding.lavAndroid.setAnimation(animationName)

        binding.lavAndroid.playAnimation()
    }

}