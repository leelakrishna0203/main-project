package com.example.bustract.User

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.bustract.R
import com.example.bustract.Responses.GetSeats
import com.example.bustract.Responses.Model.Search
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivitySeatSelectionBinding
import com.example.bustract.showToast
import com.example.bustract.spannaAble
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeatSelection : AppCompatActivity(), View.OnClickListener {
    private val bind by lazy {
        ActivitySeatSelectionBinding.inflate(layoutInflater)
    }
    val array by lazy {
        val array = ArrayList<TextView>()
        array.add(bind.a1)
        array.add(bind.a2)
        array.add(bind.a3)
        array.add(bind.a4)
        array.add(bind.a5)
        array.add(bind.a6)
        array.add(bind.a7)
        array.add(bind.a8)
        array.add(bind.a9)
        array.add(bind.a10)
        array.add(bind.a11)
        array.add(bind.a12)
        array.add(bind.a13)
        array.add(bind.a14)
        array.add(bind.a15)
        array.add(bind.a16)
        array.add(bind.a17)
        array.add(bind.a18)
        array.add(bind.a19)
        array.add(bind.a20)
        array.add(bind.a21)
        array.add(bind.a22)
        array.add(bind.a23)
        array.add(bind.a24)
        array.add(bind.a25)
        array.add(bind.a26)
        array.add(bind.a27)
        array.add(bind.a28)
        array.add(bind.a29)
        array.add(bind.a30)
        array.add(bind.a31)
        array.add(bind.a32)
        array.add(bind.a33)
        array.add(bind.a34)
        array
    }

    val checkstate by lazy {
        val array = ArrayList<Int>()
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array.add(1)
        array
    }


    val selected = ArrayList<String>()
    val alreadySelected = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)




        array.forEach {
            it.setOnClickListener(this)
        }

        val routedetails = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("routedetails", Search::class.java)
        } else {
            intent.getParcelableExtra("routedetails")
        }
        val select_date = intent.getStringExtra("select_date")

        select_date?.let { getdata(routedetails?.routeid, it) }

        if (routedetails != null && select_date != null) {
            routedetails.apply {
                bind.textView16.text = spannaAble(
                    "<b>From : </b> $fromplace<br>" +
                            "<b>To   : </b> $toplace<br>" +
                            "<b>Start Time : </b>$starttime<br>" +
                            "<b>End Time : </b>$endtime<br>" +
                            "<b>Per k/m : </b>₹$perkm/-<br>" +
                            "<b>Journey starts : </b>${select_date}/-<br>"
                )
            }
        }


        bind.book.setOnClickListener {
            if (selected.isNotEmpty()) {
                Intent(this, Billing::class.java).apply {
                    putExtra("selectedseat", selected)
                    putExtra("routedetails", routedetails)
                    putExtra("select_date", select_date)
                    startActivity(this)
                }
            } else {
                showToast("Please select Bus Seats")
            }
        }
    }


    private fun getdata(routeid: String?, selectDate: String) {

        if (routeid != null) {

            ReTrofit.instance.getAllseats(
                condition = "gettoday",
                routeid = "$routeid",
                date = selectDate
            ).enqueue(/* callback = */
                object : Callback<GetSeats> {
                    override fun onResponse(call: Call<GetSeats>, response: Response<GetSeats>) {

                        val k = response.body()?.apply {
                            var seats = ""
                            val k = data?.forEach { seats += "${it.seats}," }
                            seats.split(",").forEach { the ->
                                if (the.trim().isNotEmpty()) {
                                    alreadySelected.add(the)
                                }
                            }

                            runafuntion()
                            if (k == null) {
                                showToast("Server Error")
                                finish()
                            }
                        }
                        if (k == null) {
                            showToast(k)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<GetSeats>, t: Throwable) {
                        showToast(t.message)
                        finish()
                    }
                })
        } else {
            showToast(routeid)
            finish()
        }

    }

    private fun runafuntion() {
        array.forEach {
            if (alreadySelected.contains(it.text.toString())) {
                it.isFocusable = false
                it.isClickable = false
                it.setBackgroundResource(R.drawable.already)
                it.setTextColor(android.graphics.Color.WHITE)
            }
        }
        bind.real.isVisible = false
        bind.linearLayout2.isVisible = true
    }

    override fun onClick(p0: View?) {
        array.forEachIndexed { index, textView ->
            if (p0 == textView) {
                checkstate[index]++
                if (checkstate[index] % 2 == 0) {
                    textView.setBackgroundResource(R.drawable.select)
                    textView.setTextColor(android.graphics.Color.WHITE)
                    selected.add(textView.text.toString())
                } else {
                    selected.remove(textView.text.toString())
                    textView.setBackgroundResource(R.drawable.unselect)
                    textView.setTextColor(android.graphics.Color.BLACK)
                }

            }
        }


    }
}