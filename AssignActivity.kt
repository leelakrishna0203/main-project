package com.example.bustract.Admin

import android.app.TimePickerDialog
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.bustract.Comfun
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.Model.Route
import com.example.bustract.Responses.Model.User
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityAssignBinding
import com.example.bustract.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.Calendar

class AssignActivity : AppCompatActivity() {
    private val bind by lazy {
        ActivityAssignBinding.inflate(layoutInflater)
    }
    private val cf by lazy {
        Comfun(context = this)
    }
    val decimal = DecimalFormat("##.###")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("data2", User::class.java)
        } else {
            intent.getParcelableExtra("data2")
        }
        val route = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("data1", Route::class.java)
        } else {
            intent.getParcelableExtra("data1")
        }

        if (user != null && route != null) {

            user.apply {
                val text = "<b>Driver Name : </b>$name<br>" +
                        "<b>Driver Mail : </b>$mail<br>" +
                        "<b>Driver Mobile : </b>$mobile<br>" +
                        "<b>Driver License : </b>$drivinglicense<br>" +
                        "<b>From : </b>${route.fromplace}<br>" +
                        "<b>To&nbsp&nbsp&nbsp&nbsp: </b>${route.toplace}<br>"
                bind.fulldetails.text =
                    HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
                try {
                    val split = route.fromlatlon?.split(",")
                    val split2 = route.tolatlon?.split(",")


                    val locationa = Location("a")
                    locationa.longitude = split?.get(0)?.toDouble()!!
                    locationa.latitude = split[1].toDouble()
                    val locationb = Location("b")
                    locationb.longitude = split2?.get(0)?.toDouble()!!
                    locationb.latitude = split2[1].toDouble()
                    val km = locationa.distanceTo(locationb) / 1000

                    bind.kmcaluculation.text = HtmlCompat.fromHtml(
                        "<b>${decimal.format(km)} k/m</b>",
                        HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
                    )
                } catch (e: Exception) {
                    bind.kmcaluculation.text = HtmlCompat.fromHtml(
                        "<b>Error :${e.message}}</b>",
                        HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
                    )
                }
                bind.clock1.setOnClickListener { dialog(bind.clocktext1, "From") }
                bind.clock2.setOnClickListener { dialog(bind.clocktext2, "To") }

            }


            bind.assign.setOnClickListener {
                val start = bind.clocktext1.text.toString().trim()
                val end = bind.clocktext2.text.toString().trim()
                val days = bind.days.text.toString().trim()
                val vehiclnumber = bind.busnumber.text.toString().trim()
                val price = bind.price.text.toString().trim()
                if (start.contains("00:00")) {
                    showToast("Please select start time")
                } else if (end.contains("00:00")) {
                    showToast("Please select stop time")
                } else if (days.isEmpty()) {
                    showToast("Please enter how many days")
                } else if (vehiclnumber.isEmpty()) {
                    showToast("Please enter vehicle number")
                } else if (price.isEmpty()) {
                    showToast("Please enter the price per k/m")
                } else {

                    cf.p.show()
                    ReTrofit.instance.assignduty(
                        driverid = "${user.id}",
                        routeid = "${route.id}",
                        starttime = start,
                        endtime = end,
                        vehiclenumber = vehiclnumber,
                        perkm = price,
                        days = days
                    ).enqueue(object : Callback<CommonResponse> {
                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                            cf.p.dismiss()
                            showToast(t.message)
                        }

                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>,
                        ) {
                            cf.p.dismiss()
                            response.body()?.let {
                                if (it.message == "Success") {
                                    finish()
                                }
                                showToast(it.message)
                            }
                        }
                    })
                }
            }
        }

    }

    private fun dialog(textView: TextView, srting: String) {
        val calender = Calendar.getInstance()

        TimePickerDialog(this, { timePicker, i, i2 ->
            val amorpm = if (i < 12) {
                "AM"
            } else {
                "PM"
            }
            textView.text = HtmlCompat.fromHtml(
                "<b>${String.format("%02d : %02d", i, i2)} $amorpm</b>",
                HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
            )


        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).apply {
            setCancelable(false)
            setTitle(srting)
            show()
        }

    }
}