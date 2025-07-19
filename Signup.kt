package com.example.bustract

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityCreateBinding
import retrofit2.Call
import retrofit2.Response

class Signup : AppCompatActivity() {
    private val bind by lazy {
        ActivityCreateBinding.inflate(layoutInflater)
    }
    private val cf by lazy {
        Comfun(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)


        bind.signup.setOnClickListener {
            val name1=bind.name1.text.toString().trim()
            val email=bind.email.text.toString().trim()
            val pass=bind.pass.text.toString().trim()
            val mobile=bind.mobile.text.toString().trim()
            if(name1.isEmpty()){
            showToast("Please Enter your Name")
            }else if(email.isEmpty()){
                    showToast("Please Enter your email")
            }else if(!email.contains("@gmail.com")){
                showToast("Please enter a valid Mail")
            }else if(pass.isEmpty()){
            showToast("Please Enter your Password")
            }else if(mobile.isEmpty()){
                    showToast("Please Enter your Mobile")
            }else if(mobile.length!=10) {
                showToast("Please enter a valid mobile number")
            }else{
                cf.p.show()
                ReTrofit.instance.addUsers(name = name1, mobile =mobile,mail=email, password = pass, type = "user")
                    .enqueue(object :retrofit2.Callback<CommonResponse>{
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            response.body()?.let {
                                showToast(it.message)
                                if(it.message=="Registered SuccessFully \uD83D\uDE0A"){
                                    finish()
                                }

                            }?:{
                                showToast("Server error")
                            }
                            cf.p.dismiss()
                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        showToast(t.message)
                            cf.p.dismiss()
                        }

                    })
            }

        }
        onBackPressedDispatcher.addCallback(this,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                finish()
            }

        })


    }
}