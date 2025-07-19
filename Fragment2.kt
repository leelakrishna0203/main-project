package com.example.bustract

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import coil.load
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.DriveraddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class Fragment2:Fragment() {
    private val bind by lazy {
        DriveraddBinding.inflate(layoutInflater)
    }
    private val inter by lazy {
        activity as Myintention
    }
    private val cf by lazy {
        context?.let { Comfun(it) }
    }
    var bitmap: Bitmap?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val register=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val data=it.data
            if(data!=null){
                val uri=Uri.parse(data.data.toString())
                bitmap= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context?.contentResolver!!,uri))
                }else{
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                }
                bind.shapeableImageView.load(bitmap)

            }
        }
        bind.shapeableImageView.setOnClickListener {
            register.launch(Intent(Intent.ACTION_GET_CONTENT).apply {
                type="image/*"
            })
        }
        bind.login.setOnClickListener {
            inter.response("Login")
        }
        bind.appCompatButton2.setOnClickListener {
            val name=bind.name.text.toString().trim()
            val mail=bind.mail.text.toString().trim()
            val passwor=bind.passwor.text.toString().trim()
            val mobile=bind.mobile.text.toString().trim()
            val licensenumber=bind.licensenumber.text.toString().trim()
            if(name.isEmpty()){
                showToast("Please enter your name")
            }else if(mail.isEmpty()){
                showToast("Please enter your mail")
            }else if(passwor.isEmpty()){
                showToast("Please enter your password")
            }else if(mobile.isEmpty()){
                showToast("Please enter your mobile number")
            }else if(licensenumber.isEmpty()){
                showToast("Please enter your license number")
            }else if(mobile.length!=10){
                showToast("Please enter valid mobile number")
            }else if(licensenumber.length!=16){
                showToast("Please enter a valid Driving License number")
            }else if(bitmap==null){
                showToast("Please select a image from your gallery")
            }else{
                val out=ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG,100,out)
                cf?.p?.show()

                CoroutineScope(IO).launch {
                    ReTrofit.instance.addDriver(
                        name = name,
                        mobile = mobile,
                        mail = mail,
                        password = passwor,
                        profile = android.util.Base64.encodeToString(out.toByteArray(),android.util.Base64.NO_WRAP),
                        type = "Driver",
                        drivinglicense = licensenumber,
                        ""
                    ).enqueue(object :Callback<CommonResponse>{
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            response.body()?.let {

                            showToast(it.message)
                                if(it.message=="Success"){
                                    inter.response(it.message)
                                }

                            }?:{
                                showToast("Server Error")
                            }
                            cf?.p?.dismiss()
                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                            cf?.p?.dismiss()
                            showToast(t.message)
                        }
                    })
                }
            }


        }


        return bind.root
    }
}