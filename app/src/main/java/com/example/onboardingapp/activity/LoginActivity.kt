package com.example.onboardingapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.onboardingapp.R
import com.example.onboardingapp.databinding.ActivityLoginBinding
import com.example.onboardingapp.fragment.RegisterFragment
import com.example.onboardingapp.room.repository.RoomRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val repository = RoomRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Base_MyTheme)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    @Suppress("DEPRECATION")
    private fun init() {

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        binding.versionText.text = "v ${packageInfo.versionName}"

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            loginButton.setOnClickListener {
                when {
                    emailTxt.text.toString().isEmpty() -> Toast.makeText(
                        this@LoginActivity,
                        "El campo Email esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    !(android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt.text.toString())
                        .matches()) -> Toast.makeText(
                        this@LoginActivity,
                        "El campo Email es invalido",
                        Toast.LENGTH_LONG
                    ).show()

                    passwordText.text.toString().isEmpty() -> Toast.makeText(
                        this@LoginActivity,
                        "El campo Password esta vacio",
                        Toast.LENGTH_LONG
                    ).show()

                    passwordText.text.toString().length < 5 || passwordText.text.toString().length > 10 -> Toast.makeText(
                        this@LoginActivity,
                        "El campo Password ocupa ser de minimo 5 y maximo 10 caracteres",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> {
                        checkLogin()
                    }
                }
            }

            registerButton.setOnClickListener {
                RegisterFragment().show(supportFragmentManager, "Register")
            }
        }
    }

    private fun checkLogin() {
        lifecycleScope.launch {
            val user = repository.findUserByEmail(binding.emailTxt.text.toString())

            if (user == null || user.password != binding.passwordText.text.toString()) {
                Toast.makeText(this@LoginActivity, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this@LoginActivity, ContainerActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
    }
}
