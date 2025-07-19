package com.example.bustract

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import com.example.bustract.Admin.AdminMainActivity
import com.example.bustract.BusDepot.BusStandActivity
import com.example.bustract.Driver.DriverActivity
import com.example.bustract.Responses.CustomResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.User.UserMainActivity
import com.example.bustract.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), Myintention {
    private val bind by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val cf by lazy {
        Comfun(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        bind.framelay.isVisible = false


        bind.appCompatButton.setOnClickListener {
            val email = bind.email.text.toString().trim()
            val password = bind.password.text.toString().trim()
            if (email.isEmpty()) {
                showToast("Please enter your Email")
            } else if (password.isEmpty()) {
                showToast("Please enter your Password")
            } else if (password.lowercase() == "admin" && email.lowercase() == "admin") {
                getSharedPreferences("user", MODE_PRIVATE).edit().putString("type", "admin").apply()
                startActivity(Intent(this, AdminMainActivity::class.java))
            } else {
                cf.p.show()
                ReTrofit.instance.logindata(mail = email, password = password)
                    .enqueue(object : Callback<CustomResponse> {
                        override fun onResponse(
                            call: Call<CustomResponse>,
                            response: Response<CustomResponse>
                        ) {
                            response.body()?.let {
                                val message = it.message
                                if (message == "no data") {
                                    showToast("Invalid User")
                                } else {
                                    showToast(it.message)
                                    val k = it.data?.getOrNull(0)
                                    if (k != null) {
                                        getSharedPreferences("user", MODE_PRIVATE).edit().apply {
                                            putString("id", k.id)
                                            putString("name", k.name)
                                            putString("mobile", k.mobile)
                                            putString("mail", k.mail)
                                            putString("password", k.password)
                                            putString("profile", k.profile)
                                            putString("type", k.type)
                                            putString("drivinglicense", k.drivinglicense)
                                            apply()
                                        }
                                        when (k.type) {
                                            "user" -> {
                                                startActivity(
                                                    Intent(
                                                        this@LoginActivity,
                                                        UserMainActivity::class.java
                                                    )
                                                )
                                                finishAffinity()
                                            }

                                            "Driver" -> {
                                                startActivity(
                                                    Intent(
                                                        this@LoginActivity,
                                                        DriverActivity::class.java
                                                    )
                                                )
                                                finishAffinity()
                                            }
                                            "Deport" -> {
                                                startActivity(
                                                    Intent(
                                                        this@LoginActivity,
                                                        BusStandActivity::class.java
                                                    )
                                                )
                                                finishAffinity()
                                            }
                                        }
                                    }
                                }
                            } ?: {
                                showToast("Server Error")
                            }
                            cf.p.dismiss()
                        }

                        override fun onFailure(call: Call<CustomResponse>, t: Throwable) {
                            showToast(t.message)
                            cf.p.dismiss()
                        }
                    })
            }


        }


        bind.create.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bind.constraint3.isVisible) {
                    finish()
                } else {
                    function()
                }
            }
        })

    }

    private fun function() {
        bind.constraint3.isVisible = true
        bind.framelay.removeAllViews()
    }

    override fun response(string: String) {
        when (string) {
            "Success" -> {
                function()
            }

            "Login" -> {
                function()
            }
        }
    }


}