package com.example.bustract.User

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bustract.Comfun
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.Model.Search
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityBillingBinding
import com.example.bustract.showToast
import com.example.bustract.spannaAble
import retrofit2.Call
import retrofit2.Response

class Billing : AppCompatActivity() {
    private val bind by lazy {
        ActivityBillingBinding.inflate(layoutInflater)
    }
    val cf by lazy {
        Comfun(this)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        val selectedseat = intent.getStringArrayListExtra("selectedseat")
        val search = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("routedetails", Search::class.java)
        } else {
            intent.getParcelableExtra("routedetails")
        }

        val string = StringBuilder()

        selectedseat?.forEachIndexed { f, seats ->
            if (f == selectedseat.size - 1) {
                string.append(seats)
            } else {
                string.append("$seats,")
            }
        }
        val shared = getSharedPreferences("user", MODE_PRIVATE)
        var total = 0
        search?.apply {
            if (distance != null) {
                total = distance!!.times(selectedseat?.size!!)
            }

            val text = "<b>Name : </b>${shared.getString("name", "")}<br>" +
                    "<b>Mobile :</b> ${shared.getString("mobile", "")} <br>" +
                    "<b>Mail :</b> ${shared.getString("mail", "")} <br>" +
                    "<b>From :</b> $fromplace <br>" +
                    "<b>To :</b> $toplace <br>" +
                    "<b>Seats : </b>$string<br>" +
                    "<b>Per Km :</b> $perkm<br>" +
                    "<big>Total Payable : ₹$total/-</big><br>"
            bind.details3.text = spannaAble(text)
        }

        bind.pay.setOnClickListener {
            cf.p.show()
            ReTrofit.instance.seatPayment(
                routeid = "${search?.routeid}",
                payment = "Paid",
                seats = "$string",
                datepayed = "${intent.getStringExtra("select_date")}",
                userid = "${shared.getString("id", "")}",
                total = "$total",
                drvierid = "${search?.driverid}"
            ).enqueue(object : retrofit2.Callback<CommonResponse> {
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>,
                ) {
                    cf.p.dismiss()
                    response.body()?.apply {
                        showToast(message)
                        if (message == "Success") {
                            startActivity(Intent(this@Billing, UserMainActivity::class.java))
                            finishAffinity()
                        }
                    } ?: {
                        showToast("Server Error")
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {

                    showToast(t.message)

                    cf.p.dismiss()
                }
            })
        }


    }
}