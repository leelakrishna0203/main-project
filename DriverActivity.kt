package com.example.bustract.Driver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import coil.load
import com.example.bustract.Driver.Core.BroadCast
import com.example.bustract.Driver.Core.ViewMoreDetails
import com.example.bustract.LoginActivity
import com.example.bustract.Responses.AssignedResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.User.ModuleActivity
import com.example.bustract.databinding.ActivityDriverBinding
import com.example.bustract.showToast
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class DriverActivity : AppCompatActivity() {
    private val bind by lazy {
        ActivityDriverBinding.inflate(layoutInflater)
    }
    private val dialog by lazy {
        MaterialDatePicker.Builder.datePicker().build()
    }
    private val shared by lazy { getSharedPreferences("user", MODE_PRIVATE) }
    var vehicleNumber = ""
    private val fused by lazy { LocationServices.getFusedLocationProviderClient(this) }
    lateinit var alarma: AlarmManager
    var vehicle = ""
    var routeid = ""

    lateinit var pending: PendingIntent

    @SuppressLint("SimpleDateFormat")
    val simple = SimpleDateFormat("dd-MM-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        bind.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                pending = PendingIntent.getBroadcast(
                    this, 0, Intent(this, BroadCast::class.java),
                    PendingIntent.FLAG_MUTABLE
                )
                alarma = getSystemService(ALARM_SERVICE) as AlarmManager
                alarma.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    1000 * 10,
                    pending
                )
                showToast("Tracking Enabled")
            } else {
                alarma.cancel(pending)
                pending.cancel()


            }
        }


        bind.greetings.text = HtmlCompat.fromHtml(
            "<b>Hi ${shared.getString("name", "")} 😊 !!<b>",
            HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
        )
        shared.getString("id", "")?.let { getdata(it) }
        Log.d("userdi", "usrid:${shared.getString("id", "")} ")
        bind.profile.load(shared.getString("profile", ""))
        bind.data.text = HtmlCompat.fromHtml(
            "${shared.getString("Assigned", "")}",
            HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
        )

        showToast("$vehicle")
        bind.cardView4.setOnClickListener {
            val intent = Intent(this@DriverActivity, UpdateRouteActivity::class.java)
            startActivity(intent)
        }
        /* bind.cardView4.setOnClickListener {
             dialog.show(supportFragmentManager, "Choose a date???")
         }*/



        dialog.addOnPositiveButtonClickListener {
            Intent(this, ViewMoreDetails::class.java).apply {
                putExtra("date", simple.format(Date(it)))
                startActivity(this)
            }
        }




        bind.logout1.setOnClickListener {
            MaterialAlertDialogBuilder(this).apply {
                setCancelable(false)
                setMessage("Do you want to Logout ?? \n Press 'Yes' to Logout or Press 'No' to cancel")
                setPositiveButton("Yes") { c, _ ->
                    c.dismiss()
                    getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                    finishAffinity()
                    startActivity(Intent(this@DriverActivity, ModuleActivity::class.java))
                }
                setNegativeButton("No") { p, _ ->
                    p.dismiss()
                }
                show()
            }


        }


    }

    private fun getdata(id: String) {
        ReTrofit.instance.getData(condition = "getassigned", id = id)
            .enqueue(object : Callback<AssignedResponse> {
                override fun onResponse(
                    call: Call<AssignedResponse>,
                    response: Response<AssignedResponse>,
                ) {
                    response.body()?.let {
                        if (it.data?.isNotEmpty() == true) {
                            val view = it.data!![0]
                            Log.d("fkdsfkjdk", "the list : ${it.data}")
                            shared.edit().putString("rrr","${view.vehiclenumber}").apply()
                            shared.edit().putString("vvv","${view.routeid}").apply()

                            val text = "<b>From : </b>${view.fromplace}<br>" +
                                    "<b>To  : </b>${view.toplace}<br>" +
                                    "<b>Starts at : </b>${view.starttime}<br>" +
                                    "<b>Ends : </b>${view.endtime}<br>" +
                                    "<b>Per km : </b>${view.perkm} km/s<br>"

                            getSharedPreferences("user", MODE_PRIVATE).apply {
                                edit().putString("vehicleNumber", view.vehiclenumber).apply()
                                val k = getString("Assigned", "")
                                if (k != text) {
                                    edit().putString("Assigned", text).apply()

                                }
                                bind.data.text = HtmlCompat.fromHtml(
                                    text,
                                    HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
                                )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<AssignedResponse>, t: Throwable) {
                    Log.i(packageName, "${t.message}")
                }
            })

    }


    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onStart() {
        super.onStart()

    }


}
