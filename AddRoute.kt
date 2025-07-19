package com.example.bustract.Admin

import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.bustract.Comfun
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityAddRouteBinding
import com.example.bustract.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.concurrent.CountDownLatch

class AddRoute : AppCompatActivity() {

    private val bind by lazy {
        ActivityAddRouteBinding.inflate(layoutInflater)
    }
    val mutable= MutableLiveData<String>()

    private val cf by lazy {
        Comfun(this)
    }
    val decimal=DecimalFormat("##.#######")
    lateinit var  geocoder :Geocoder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(bind.root)
        geocoder=Geocoder(this)
        bind.getlatlon.setOnClickListener {
            getlatlon()
        }
        val textview=bind.fromtolalton
        mutable.observe(this){
            textview.text=it
        }
        bind.add.setOnClickListener {
            val k=textview.text.split(",")
            if(k.size!=4){
                getlatlon()
            }else{
                val from=bind.from.text.toString().trim()
                val to=bind.to.text.toString().trim()
                cf.p.show()
                ReTrofit.instance.addroute(
                    fromplace = from,
                    toplace = to,
                    fromlatlon = "${k[0].trim()},${k[1].trim()}",
                    tolatlon = "${k[2].trim()},${k[3].trim()}",
                    driverid = "not assigned"
                ).enqueue(
                    object :Callback<CommonResponse>{
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            cf.p.dismiss()
                            response.body()?.apply {
                                showToast(message)
                                if(message=="Added") {
                                    intent?.let {
                                        it.putExtra("result",message)
                                        setResult(RESULT_OK,intent)
                                        finish()
                                    }
                                }

                            }?:run{
                            showToast("Server Error")
                            }
                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                                cf.p.dismiss()
                            showToast(t.message)
                        }

                    }
                )
            }
        }


    }

    private fun getlatlon() {
        val from=bind.from.text.toString().trim()
        val to=bind.to.text.toString().trim()

        if(from.isEmpty()){
            showToast("Please enter the from Address")
        }else if(to.isEmpty()) {
            showToast("Please enter the To Address")
        }else {

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    var firstString=""
                    var secondString=""
                    val latch = CountDownLatch(2) // To wait for both results

                    // Get "from" coordinates
                    geocoder.getFromLocationName(from, 1, object : Geocoder.GeocodeListener {
                        override fun onGeocode(locations: MutableList<android.location.Address>) {
                            if (locations.isNotEmpty()) {
                                val k = locations[0]
                                firstString = "${decimal.format(k.latitude)},${decimal.format(k.longitude)}"
                            }
                            latch.countDown()
                        }

                        override fun onError(errorMessage: String?) {
                            latch.countDown() // Ensure latch releases even on error
                        }
                    })

                    // Get "to" coordinates
                    geocoder.getFromLocationName(to, 1, object : Geocoder.GeocodeListener {
                        override fun onGeocode(locations: MutableList<android.location.Address>) {
                            if (locations.isNotEmpty()) {
                                val l = locations[0]
                                secondString = "${decimal.format(l.latitude)},${decimal.format(l.longitude)}"
                            }
                            latch.countDown()
                        }

                        override fun onError(errorMessage: String?) {
                            latch.countDown()
                        }
                    })

                    // Wait for both results
                    latch.await() // Blocks until both callbacks complete
                    mutable.postValue("$secondString,\n$firstString") // Update on main thread

                }else{
                    val fri=geocoder.getFromLocationName(from,1)?.get(0)
                    val second=geocoder.getFromLocationName(to,1)?.get(0)

                    mutable.value="${decimal.format(fri?.latitude)},${decimal.format(fri?.longitude)},\n${decimal.format(second?.latitude)},${decimal.format(second?.longitude)}"
                }
            }catch (e:Exception){
                mutable.value="Sorry Invalid LatLon"
            }

        }
    }
}