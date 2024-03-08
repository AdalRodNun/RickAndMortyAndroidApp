package com.myapp.rickandmorty.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ActivityLoginBinding
import com.myapp.rickandmorty.ui.fragment.RegisterFragment
import com.myapp.rickandmorty.ui.fragment.RegisterFragment.Companion.TAG
import com.myapp.rickandmorty.ui.viewModel.LoginViewModel
import com.myapp.rickandmorty.utils.ExtendedFunctions.getPackageInfoCompat
import com.myapp.rickandmorty.utils.Functions.isValidEmail
import com.myapp.rickandmorty.utils.SimpleTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
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

        etEmail.addTextChangedListener(SimpleTextWatcher {
            tilEmail.error = null
        })

        etPassword.addTextChangedListener(SimpleTextWatcher {
            tilPassword.error = null
        })
    }

    private fun getObservers() = with(viewModel) {
        checked.observe(this@LoginActivity) {
            if (it) {
                val intent = Intent(
                    this@LoginActivity,
                    ContainerActivity::class.java
                )
                finish()
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Correo o contraseña incorrectos",
                    Toast.LENGTH_LONG
                ).show()
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
}
