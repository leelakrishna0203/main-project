package com.example.bustract.Admin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import coil.load
import com.example.bustract.Comfun
import com.example.bustract.Myintention
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.User.LocationUtils
import com.example.bustract.User.MapsActivity
import com.example.bustract.databinding.ActivityAddTypeBinding
import com.example.bustract.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class AddTypeActivity : AppCompatActivity() {
    private val bind by lazy { ActivityAddTypeBinding.inflate(layoutInflater) }

    private val inter by lazy {
        this as Myintention
    }
    private lateinit var gestureDetector: GestureDetector
    private lateinit var getRequestLauncher: ActivityResultLauncher<Intent>
    private var lat: Double? = null
    private var lng: Double? = null
    var textcheck = ""
    private val cf by lazy {
        this.let { Comfun(it) }
    }
    var bitmap: Bitmap? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        getRequestLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    lat = data?.getDoubleExtra("latitude", 0.0)
                    lng = data?.getDoubleExtra("longitude", 0.0)


                    if (lat != null && lng != null) {
                        val address = LocationUtils.getAddressFromLatLng(
                            applicationContext,

                            lat!!,
                            lng!!
                        )
                        showToast("$lat,$lng")
                        textcheck = "$lat,$lng"

                        bind.deportlocation.setText(address)
                    }


                }
            }

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                val intent = Intent(this@AddTypeActivity, MapsActivity::class.java)
                getRequestLauncher.launch(intent)
                return true
            }
        })

        bind.deportlocation.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
        val roleOfADD = intent.getStringExtra("key")!!
        showToast("$roleOfADD")
        if (roleOfADD == "Driver") {
            bind.textInputLayout14.visibility = View.GONE
        } else {
            bind.textInputLayout13.visibility = View.GONE
        }

        val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val data = it.data
            if (data != null) {
                val uri = data.data.toString().toUri()
                bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            this.contentResolver!!,
                            uri
                        )
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                }
                bind.shapeableImageView.load(bitmap)

            }
        }
        bind.shapeableImageView.setOnClickListener {
            register.launch(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            })
        }
        bind.login.setOnClickListener {
            inter.response("Login")
        }
        bind.appCompatButton2.setOnClickListener {
            val name = bind.name.text.toString().trim()
            val mail = bind.mail.text.toString().trim()
            val passwor = bind.passwor.text.toString().trim()
            val mobile = bind.mobile.text.toString().trim()
            val licensenumber = bind.licensenumber.text.toString().trim()
            val deprtlocat = bind.deportlocation.text.toString().trim()
            if (name.isEmpty()) {
                showToast("Please enter your name")
            } else if (mail.isEmpty()) {
                showToast("Please enter your mail")
            } else if (passwor.isEmpty()) {
                showToast("Please enter your password")
            } else if (mobile.isEmpty()) {
                showToast("Please enter your mobile number")
            } else if (roleOfADD == "Driver" && licensenumber.isEmpty()) {
                showToast("Please enter your license number")
            } else if (roleOfADD == "Driver" && licensenumber.length != 16) {
                showToast("Please enter a valid Driving License number")
            }
            else if (roleOfADD != "Driver" && deprtlocat.isEmpty()) {
                showToast("Please Double Tap and set the location")
            }else if (mobile.length != 10) {
                showToast("Please enter valid mobile number")
            }  else if (bitmap == null) {
                showToast("Please select a image from your gallery")
            } else {
                val out = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out)
                cf?.p?.show()

                CoroutineScope(IO).launch {
                    ReTrofit.instance.addDriver(
                        name = name,
                        mobile = mobile,
                        mail = mail,
                        password = passwor,
                        profile = android.util.Base64.encodeToString(
                            out.toByteArray(),
                            android.util.Base64.NO_WRAP
                        ),
                        type = "$roleOfADD",
                        drivinglicense = licensenumber,
                        textcheck
                    ).enqueue(object : Callback<CommonResponse> {
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>,
                        ) {
                            response.body()?.let {

                                showToast(it.message)
                                if (it.message == "Success") {
                                    inter.response(it.message)
                                }

                            } ?: {
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

    }
}