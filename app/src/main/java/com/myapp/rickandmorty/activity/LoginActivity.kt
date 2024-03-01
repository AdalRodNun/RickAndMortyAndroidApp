package com.myapp.rickandmorty.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.databinding.ActivityLoginBinding
import com.myapp.rickandmorty.fragment.RegisterFragment
import com.myapp.rickandmorty.fragment.RegisterFragment.Companion.TAG
import com.myapp.rickandmorty.repository.RoomRepository
import com.myapp.rickandmorty.utils.ExtendedFunctions.getPackageInfoCompat
import com.myapp.rickandmorty.utils.Functions.isValidEmail
import com.myapp.rickandmorty.utils.SimpleTextWatcher
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val repository = RoomRepository()

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

    // TODO - Refactor to viewModel
    private fun checkLogin() {
        lifecycleScope.launch {
            val user = repository.findUserByEmail(binding.etEmail.text.toString())

            if (user == null || user.password != binding.etPassword.text.toString()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Correo o contraseña incorrectos",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(
                    this@LoginActivity,
                    ContainerActivity::class.java
                )
                finish()
                startActivity(intent)
            }
        }
    }
}
